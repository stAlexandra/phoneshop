package com.es.core.service.user;

import com.es.core.dao.LevelDao;
import com.es.core.dao.UserDao;
import com.es.core.dao.UserLevelDiscountDao;
import com.es.core.exception.DuplicateDiscountException;
import com.es.core.model.discount.Discount;
import com.es.core.model.discount.DiscountValueType;
import com.es.core.model.user.Level;
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
    private UserLevelDiscountDao userLevelDiscountDao;
    @Resource
    private UserDao userDao;
    @Resource
    private LevelDao levelDao;
    @Resource
    private UserDiscountsService userDiscountsService;

    @Override
    public Optional<Double> getDiscountPercentage(Integer userLevel) {
        Optional<Double> levelDiscount = Optional.empty();
        if (userLevel != null) {
            Discount discount = userLevelDiscountDao.getByUserLevel(userLevel);
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
            Discount discount = userLevelDiscountDao.getByUserLevel(user.getLevel().getNumber());
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

    @Override
    public void addXP(User user, Long xp) {
        if (xp == null || xp.equals(0L)) return;
        if (user.getLevel() == null || user.getLevel().getNumber() == 0) {
            user.setXp(0);
            return;
        }
        long futureXP = user.getXp() + xp;
        if (futureXP >= user.getLevel().getMaxXP()) {
            levelUp(user);
        }
        user.setXp(futureXP);
        userDao.save(user);
    }

    private void levelUp(User user) {
        int currentLevelNum = user.getLevel().getNumber();
        Level upLevel = levelDao.getByNumber(currentLevelNum+1);
        if (upLevel != null) {
            user.setLevel(upLevel);
        }
    }
}
