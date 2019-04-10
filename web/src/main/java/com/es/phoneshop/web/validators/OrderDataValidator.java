package com.es.phoneshop.web.validators;

import com.es.core.model.stock.Stock;
import com.es.core.service.CartService;
import com.es.core.service.StockService;
import com.es.phoneshop.web.dataview.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDataValidator implements Validator {
    private StockService stockService;
    private CartService cartService;

    @Value("${outOfStock.orderData}")
    private String outOfStockMessage;

    @Autowired
    public OrderDataValidator(CartService cartService, StockService stockService) {
        this.cartService = cartService;
        this.stockService = stockService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderData.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryAddress", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPhoneNo", "required");

        OrderData orderData = (OrderData) obj;

        List<Long> orderPhoneIds = new ArrayList<>(orderData.getPhoneIdToQuantity().keySet());
        List<Stock> stockList = stockService.getStocks(orderPhoneIds);

        List<Long> outOfStockPhoneIds = stockList.stream().filter(stock -> {
            Long phoneId = stock.getPhone().getId();
            return stock.getStock() - stock.getReserved() < orderData.getPhoneIdToQuantity().get(phoneId);
        }).map(stock -> stock.getPhone().getId()).collect(Collectors.toList());

        for(Long id : outOfStockPhoneIds){
            errors.rejectValue("phoneIdToQuantity['" + id + "']", "outOfStock", outOfStockMessage);
        }
    }
}
