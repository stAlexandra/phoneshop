package com.es.core.model;

import com.es.core.model.order.Order;
import com.es.core.model.user.User;

public class Context {
    private User user;
    private Order order;

    public Context(User user) {
        this.user = user;
    }

    public Context(User user, Order order) {
        this.user = user;
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
