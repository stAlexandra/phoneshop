package st.alexandra.facades.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UpdateCartItemRequestData {
    private Long id;

    @NotNull
    @Min(value = 1L)
    @Max(value = 20L)
    private Long quantity;

    public UpdateCartItemRequestData() {
    }

    public UpdateCartItemRequestData(Long id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
