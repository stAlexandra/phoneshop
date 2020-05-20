package st.alexandra.facades.dto;

import com.es.core.model.cart.Cart;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


public class UpdateCartRequestData {
    @Valid
    private List<UpdateCartItemRequestData> cartItemDataList;

    public UpdateCartRequestData() {
        this.cartItemDataList = new ArrayList<>();
    }

    public UpdateCartRequestData(Cart cart) {
        this.cartItemDataList = new ArrayList<>();
        cart.getItems().forEach(item ->
                this.cartItemDataList.add(new UpdateCartItemRequestData(item.getProduct().getId(), item.getQuantity())));
    }

    public List<UpdateCartItemRequestData> getCartItemDataList() {
        return cartItemDataList;
    }

    public void setCartItemDataList(List<UpdateCartItemRequestData> cartItemDataList) {
        this.cartItemDataList = cartItemDataList;
    }
}
