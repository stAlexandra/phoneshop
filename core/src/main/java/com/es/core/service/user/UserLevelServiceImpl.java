package com.es.core.service.user;

import com.es.core.dao.UserLevelDiscountDao;
import com.es.core.exception.DuplicateDiscountException;
import com.es.core.model.discount.Discount;
import com.es.core.model.discount.DiscountValueType;
import com.es.core.model.user.User;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

@Service
public class UserLevelServiceImpl implements UserLevelService {
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
                BigDecimal discountValue = discount.getValue().multiply(new BigDecimal(100d), new MathContext(2));
                return Optional.of(discountValue.doubleValue());
            } else return Optional.empty();
        }
        return levelDiscount;
    }

    @Override
    public boolean addLevelDiscountForUser(@NotNull User user) {
        try {
            Discount discount = userLevelToDiscountDao.getByUserLevel(user.getLevel().getNumber());
            userDiscountsService.addDiscount(user, discount);
        } catch (DuplicateDiscountException exc) {
            System.out.println("Level discount for user " + user.getName() + " is already activated");
            return false;
        } catch (DataAccessException exc) {
            System.out.println("Can't activate discount for user " + user.getName());
            return false;
        }
        return true;
    }
}
