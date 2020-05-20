package com.es.core.model.order;

import com.es.core.model.product.Product;

import java.util.Objects;

public class OrderItem {
    private Product product;
    private Order order;
    private Integer quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(getProduct(), orderItem.getProduct()) &&
                Objects.equals(getOrder(), orderItem.getOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProduct(), getOrder());
    }
}
