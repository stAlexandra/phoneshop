package st.alexandra.facades.impl;

import com.es.core.exception.CouponNotFoundException;
import com.es.core.model.cart.Cart;
import com.es.core.model.discount.Coupon;
import com.es.core.model.discount.Discount;
import com.es.core.model.user.User;
import com.es.core.service.checkout.CartDiscountService;
import com.es.core.service.discount.CouponService;
import com.es.core.service.discount.DiscountService;
import com.es.core.service.user.UserDiscountsService;
import com.es.core.service.user.UserLevelService;
import com.es.core.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import st.alexandra.facades.PromotionsFacade;

import java.util.Set;

@Component
public class PromotionsFacadeImpl implements PromotionsFacade {
    private UserService userService;
    private UserDiscountsService userDiscountsService;
    private UserLevelService userLevelService;
    private DiscountService discountService;
    private CouponService couponService;
    private CartDiscountService cartDiscountService;

    @Override
    public boolean activateCartCoupon(String userName, String couponCode) throws CouponNotFoundException {
        // TODO handle exception
        Coupon coupon = couponService.getCouponByCode(couponCode).orElseThrow(() -> new CouponNotFoundException(couponCode));
        if (cartDiscountService.hasDiscount(coupon)) {
            return false;
        } else {
            return discountService.applyDiscount(coupon);
        }
    }

    @Override
    public void fetchAndApplyCartDiscounts(String userName, Cart cart) {
        //TODO fetch discounts from providers
        Set<Discount> discounts = userDiscountsService.getActiveDiscounts(userName);

        // Get discounts that are not yet applied in cart
        Set<Discount> inactiveDiscounts = cartDiscountService.getInactiveDiscounts(cart, discounts);
        if (inactiveDiscounts != null && !inactiveDiscounts.isEmpty()) {
            discountService.applyDiscount(inactiveDiscounts); // TODO use return value
        }
    }

    @Override
    public void initUser(String userName) {
        User user = userService.getUserByName(userName);
        if (user != null) {
            addUserLevelDiscount(user);
        }
    }

    private void addUserLevelDiscount(User user) {
        if (userLevelService.addLevelDiscountForUser(user)) {
            //TODO log
            System.out.println("Level discount activated for user " + user.getName());
        } else {
            System.out.println("Could not activate discount for user");
        }
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

    @Autowired
    public void setUserLevelService(UserLevelService userLevelService) {
        this.userLevelService = userLevelService;
    }

    @Autowired
    public void setCartDiscountService(CartDiscountService cartDiscountService) {
        this.cartDiscountService = cartDiscountService;
    }
}
