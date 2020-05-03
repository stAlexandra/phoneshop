package com.es.core.service.user;

import com.es.core.dao.UserLevelDiscountDao;
import com.es.core.exception.DuplicateDiscountException;
import com.es.core.model.discount.Discount;
import com.es.core.model.discount.DiscountValueType;
import com.es.core.model.user.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class UserLevelDiscountServiceImpl implements UserLevelDiscountService {
    @Resource
    private UserLevelDiscountDao userLevelToDiscountDao;
    @Resource
    private UserDiscountsService userDiscountsService;

    @Override
    public Optional<Double> getDiscountPercentage(Integer userLevel) {
        Optional<Double> levelDiscount = Optional.empty();
        if (userLevel != null) {
            Discount discount = userLevelToDiscountDao.getByUserLevel(userLevel);
            if (discount == null) return Optional.empty();

            if (DiscountValueType.PERCENTAGE.equals(discount.getValueType())) {
                return Optional.of(discount.getValue().doubleValue());
            } else return Optional.empty();
        }
        return levelDiscount;
    }

    @Override
    public boolean activateLevelDiscountForUser(@NotNull User user) {
        // TODO check if user can activate level discount twice
        Discount discount = userLevelToDiscountDao.getByUserLevel(user.getLevel());
        try {
            userDiscountsService.addDiscount(user, discount);
        } catch (DuplicateDiscountException exc) {
            System.out.println("Level discount for user " + user.getName() + " is already activated");
            return false;
        }
        return true;
    }
}
