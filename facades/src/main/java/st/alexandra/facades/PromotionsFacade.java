package st.alexandra.facades;

import com.es.core.model.cart.Cart;

public interface PromotionsFacade {
    boolean activateCartCoupon(String userName, String couponCode);
    void initUser(String userName);
//    void addUserLevelDiscount(String userName);
    void fetchAndApplyCartDiscounts(String userName, Cart cart);
}
