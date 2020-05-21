package st.alexandra.facades.impl;

import com.es.core.model.order.Order;
import com.es.core.model.user.User;
import com.es.core.service.checkout.OrderService;
import com.es.core.service.user.UserLevelService;
import com.es.core.service.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import st.alexandra.facades.UserFacade;
import st.alexandra.facades.dto.AchievementData;
import st.alexandra.facades.dto.UserData;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserFacadeImpl implements UserFacade {
    @Resource
    private UserService userService;

    @Resource
    private UserLevelService userLevelService;

    @Resource
    private OrderService orderService;

    @Override
    public UserData getUserData(String userName) {
        User user = userService.getUserByName(userName);

        UserData userData = new UserData();
        if (user != null) {
            userLevelService.getDiscountPercentage(user.getLevel().getNumber()).ifPresent(userData::setDiscountPercentage);
            userData.setLevel(user.getLevel().getNumber());
            userData.setAchievements(getAchievements(user));
            userData.setXP(user.getXp());
            userData.setMaxXP(user.getLevel().getMaxXP());
        }
        return userData;
    }

    @Override
    public UserData getUserData(Principal principal) {
        if (principal == null || StringUtils.isEmpty(principal.getName())) {
            return null;
        }
        return getUserData(principal.getName());
    }

    private List<AchievementData> getAchievements(User user) {
        return user.getAchievements().stream().map(achievement -> {
            AchievementData achievementData = new AchievementData();
            achievementData.setName(achievement.getName());
            achievementData.setDescription(achievement.getDescription());
            return achievementData;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Order> getUserOrders(String userName) {
        return orderService.getUserOrders(userName);
    }

    //    @Override
//    public UserData getUserLevelData(String userName) {
//        User user = getUser(userName);
//        return getUserLevelData(user);
//    }
//
//    @Override
//    public UserData getUserLevelData(Principal principal) {
//        User user = getUser(principal);
//        return getUserLevelData(user);
//    }
//
//    @Override
//    public UserData getUserLevelData(User user) {
//        UserData userData = new UserData();
//        if (user != null) {
//            userLevelService.getDiscountPercentage(user.getLevel()).ifPresent(userData::setDiscountPercentage);
//            userData.setLevel(user.getLevel());
//        }
//        return userData;
//    }
}
