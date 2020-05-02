package st.alexandra.facades;

import com.es.core.exception.CouponNotFoundException;
import com.es.core.model.discount.Coupon;
import com.es.core.model.user.User;
import com.es.core.service.discount.CouponService;
import com.es.core.service.discount.DiscountService;
import com.es.core.service.user.UserDiscountsService;
import com.es.core.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromotionsFacadeImpl implements PromotionsFacade {
    private UserService userService;
    private UserDiscountsService userDiscountsService;
    private DiscountService discountService;
    private CouponService couponService;

    @Override
    public boolean activateCartCoupon(String userName, String couponCode) throws CouponNotFoundException {
        User user = userService.getUserByName(userName);
        // TODO handle exception
        Coupon coupon = couponService.getCouponByCode(couponCode).orElseThrow(() -> new CouponNotFoundException(couponCode));
        if (!userDiscountsService.hasDiscount(user, coupon) && discountService.applyDiscount(coupon)) {
            userDiscountsService.addDiscount(user, coupon);
            return true;
        } else return false;
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

    @Autowired
    public void setUserDiscountsService(UserDiscountsService userDiscountsService) {
        this.userDiscountsService = userDiscountsService;
    }
}
