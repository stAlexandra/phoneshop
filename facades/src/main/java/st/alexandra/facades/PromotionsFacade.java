package st.alexandra.facades;

public interface PromotionsFacade {
    boolean activateCartCoupon(String userName, String couponCode);
    void activateUserLevelDiscount(String userName);
}
