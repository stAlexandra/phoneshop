package st.alexandra.facades.impl;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.CustomerInfo;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.service.checkout.OrderService;
import org.springframework.stereotype.Component;
import st.alexandra.facades.OrderFacade;
import st.alexandra.facades.dto.OrderData;

import java.util.List;

@Component
public class OrderFacadeImpl implements OrderFacade {
    private final OrderService orderService;

    public OrderFacadeImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Order createOrder(Cart cart, OrderData orderData) {
        CustomerInfo customerInfo = new CustomerInfo(orderData.getFirstName(), orderData.getLastName(),
                orderData.getDeliveryAddress(), orderData.getContactPhoneNo());
        return orderService.createOrder(cart, customerInfo);
    }

    @Override
    public void placeOrder(Order order) {
        orderService.placeOrder(order);
    }

    @Override
    public Order getOrderBySecureId(String secureId) {
        return orderService.getOrderBySecureId(secureId);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderService.getOrderById(id);
    }

    @Override
    public List<Order> getAllOrdersSortedByDate() {
        return orderService.getAllOrdersSortedByDate();
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus status) {
        orderService.updateOrderStatus(id, status);
    }
}
