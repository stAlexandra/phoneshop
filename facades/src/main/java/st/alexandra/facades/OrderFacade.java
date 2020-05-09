package st.alexandra.facades;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import st.alexandra.facades.dto.OrderData;

import java.util.List;

public interface OrderFacade {
    Order createOrder(Cart cart, OrderData orderData);

    void placeOrder(Order order);

    Order getOrderBySecureId(String secureId);

    Order getOrderById(Long id);

    List<Order> getAllOrdersSortedByDate();

    void updateOrderStatus(Long id, OrderStatus status);
}
