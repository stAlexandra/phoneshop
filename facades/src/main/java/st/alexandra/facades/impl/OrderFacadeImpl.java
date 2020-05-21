package st.alexandra.facades.impl;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.CustomerInfo;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.service.achievement.AchievementService;
import com.es.core.service.checkout.OrderService;
import org.springframework.stereotype.Component;
import st.alexandra.facades.OrderFacade;
import st.alexandra.facades.dto.OrderData;

import java.util.List;

@Component
public class OrderFacadeImpl implements OrderFacade {
    private final OrderService orderService;
    private final AchievementService achievementService;

    public OrderFacadeImpl(OrderService orderService, AchievementService achievementService) {
        this.orderService = orderService;
        this.achievementService = achievementService;
    }

    @Override
    public Order createOrder(Cart cart, OrderData orderData) {
        CustomerInfo customerInfo = new CustomerInfo(orderData.getFirstName(), orderData.getLastName(),
                orderData.getDeliveryAddress(), orderData.getContactPhoneNo());
        return orderService.createOrder(cart, customerInfo);
    }

    @Override
    public void placeOrder(Order order, String userName) {
        orderService.placeOrder(order);
        orderService.saveOrderForUser(order, userName);
        achievementService.checkForOrderAchievements(order, userName);
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
