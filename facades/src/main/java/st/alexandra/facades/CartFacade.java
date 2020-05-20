package st.alexandra.facades;

import com.es.core.model.cart.Cart;
import st.alexandra.facades.dto.UpdateCartRequestData;

public interface CartFacade {
    Cart getCart();

    void add(Long phoneId, Long quantity);

    void update(UpdateCartRequestData updateCartRequestData);

    boolean remove(Long phoneId);

//    void remove(Collection<Long> phoneIdList);
//
//    void clearCart();
//
//    void recalculateCart();

}
