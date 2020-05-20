package com.es.core.model.stock;

import com.es.core.model.product.Product;

public class Stock {
    private Product product;
    private Integer stock;
    private Integer reserved;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getReserved() {
        return reserved;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
    }
}
