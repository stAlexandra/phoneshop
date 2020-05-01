package st.alexandra.facades;

import com.es.core.model.discount.Coupon;
import com.es.core.model.user.User;
import com.es.core.service.DiscountService;
import com.es.core.service.UserService;
import com.es.core.service.businesslogic.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PromotionsFacadeImpl implements PromotionsFacade {
    private UserService userService;
    private DiscountService discountService;
    private CouponService couponService;

    @Override
    public boolean activateCartCoupon(String userName, String couponCode) {
        User user = userService.getUserByName(userName);
        Optional<Coupon> optCoupon = couponService.getCouponByCode(couponCode);
        // TODO: set activated coupons to user
        return optCoupon.filter(coupon -> discountService.applyDiscount(coupon)).isPresent();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Autowired
    public void setCouponService(CouponService couponService) {
        this.couponService = couponService;
    }
}
