package com.es.phoneshop.web.validators;

import com.es.core.model.stock.Stock;
import com.es.core.service.StockService;
import st.alexandra.facades.dto.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDataValidator implements Validator {
    private StockService stockService;

    @Autowired
    public OrderDataValidator(StockService stockService) {
        this.stockService = stockService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderData.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        OrderData orderData = (OrderData) obj;

        List<Long> orderPhoneIds = new ArrayList<>(orderData.getPhoneIdToQuantity().keySet());
        List<Stock> stockList = stockService.getStocks(orderPhoneIds);

        stockList.stream()
                .filter(stock -> {
                    Long phoneId = stock.getPhone().getId();
                    return stock.getStock() - stock.getReserved() < orderData.getPhoneIdToQuantity().get(phoneId);
                })
                .forEach(stock -> errors.rejectValue("phoneIdToQuantity['" + stock.getPhone().getId() + "']", "outOfStock"));
    }
}
