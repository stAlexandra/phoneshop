package st.alexandra.facades.impl;

import com.es.core.model.cart.Cart;
import com.es.core.service.checkout.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import st.alexandra.facades.CartFacade;
import st.alexandra.facades.dto.UpdateCartItemRequestData;
import st.alexandra.facades.dto.UpdateCartRequestData;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CartFacadeImpl implements CartFacade {
    private final CartService cartService;

    @Autowired
    public CartFacadeImpl(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public Cart getCart() {
        return cartService.getCart();
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        cartService.addPhone(phoneId, quantity);
    }

    @Override
    public void update(UpdateCartRequestData updateCartRequestData) {
        Map<Long, Long> phoneIdToQuantity = updateCartRequestData.getCartItemDataList().stream()
                .collect(Collectors.toMap(UpdateCartItemRequestData::getPhoneId, UpdateCartItemRequestData::getQuantity));
        cartService.update(phoneIdToQuantity);
    }

    @Override
    public boolean remove(Long phoneId) {
        return cartService.remove(phoneId);
    }

////    @Override
//    public void remove(Collection<Long> phoneIdList) {
//        cartService.remove(phoneIdList);
//    }
//
////    @Override
//    public void clearCart() {
//        cartService.clearCart();
//    }
//
////    @Override
//    public void recalculateCart() {
//        cartService.recalculateCart();
//    }
}
