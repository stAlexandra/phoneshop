package com.es.core.service.user;

import com.es.core.dao.UserDiscountsDao;
import com.es.core.model.discount.Discount;
import com.es.core.model.user.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserDiscountsServiceImpl implements UserDiscountsService {
    private final UserDiscountsDao userDiscountsDao;

    public UserDiscountsServiceImpl(UserDiscountsDao userDiscountsDao) {
        this.userDiscountsDao = userDiscountsDao;
    }

    @Override
    public void addDiscount(User user, Discount discount) {
        if (discount != null) {
            userDiscountsDao.save(user.getName(), discount);
        } else {
            // TODO logger
            System.out.println("Discount could not be null");
        }
    }

    @Override
    public void removeDiscount(User user, Discount discount) {
        //TODO implement
    }

    @Override
    public boolean hasDiscount(User user, Discount discount) {
        Collection<Discount> discounts = userDiscountsDao.getByUserName(user.getName());
        return discounts.contains(discount);
    }
}
