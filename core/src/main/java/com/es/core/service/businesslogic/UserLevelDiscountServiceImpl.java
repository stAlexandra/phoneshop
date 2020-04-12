package com.es.core.service.businesslogic;

import com.es.core.dao.UserLevelToDiscountDao;
import com.es.core.model.discount.Discount;
import com.es.core.model.discount.DiscountValueType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class UserLevelDiscountServiceImpl implements UserLevelDiscountService {
    @Resource
    UserLevelToDiscountDao userLevelToDiscountDao;

    @Override
    public Optional<Double> getDiscountPercentage(Integer userLevel) {
        Optional<Double> levelDiscount = Optional.empty();
        if (userLevel != null) {
            Discount discount = userLevelToDiscountDao.getDiscountByUserLevel(userLevel);
            if (discount == null) return Optional.empty();

            if (DiscountValueType.PERCENTAGE.equals(discount.getValueType())) {
                return Optional.of(discount.getValue().doubleValue());
            } else return Optional.empty();
        }
        return levelDiscount;
    }
}
