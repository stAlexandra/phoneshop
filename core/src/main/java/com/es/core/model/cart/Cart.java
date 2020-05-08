package com.es.core.model.cart;

import com.es.core.model.discount.Discount;

import java.math.BigDecimal;
import java.util.*;

public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;
    private BigDecimal totalDiscount;
    private BigDecimal subtotalPrice;
    private Set<Discount> discounts;

    public Cart() {
        this.items = new ArrayList<>();
        this.totalPrice = BigDecimal.ZERO;
        this.totalDiscount = BigDecimal.ZERO;
        this.discounts = new HashSet<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public BigDecimal getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(BigDecimal subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }

    public Set<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Set<Discount> discounts) {
        this.discounts = discounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(getItems(), cart.getItems()) &&
                Objects.equals(getTotalPrice(), cart.getTotalPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItems(), getTotalPrice());
    }
}
