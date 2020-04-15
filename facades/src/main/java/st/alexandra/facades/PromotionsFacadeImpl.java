package st.alexandra.facades;

import com.es.core.model.user.User;
import com.es.core.service.DiscountService;
import com.es.core.service.OrderService;
import com.es.core.service.UserService;

import javax.annotation.Resource;

public class PromotionsFacadeImpl implements PromotionsFacade {
    @Resource
    private UserService userService;

    @Resource
    private DiscountService discountService;

    @Resource
    private OrderService orderService;

    @Override
    public boolean activateOrderCoupon(Long orderId, String userName) {
//        User user = userService.getUserByName(userName);
//        orderService.getOrderById(orderId);
        return true;
    }
}
