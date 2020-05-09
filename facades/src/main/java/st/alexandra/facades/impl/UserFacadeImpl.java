package st.alexandra.facades.impl;

import com.es.core.model.user.User;
import com.es.core.service.user.UserLevelService;
import com.es.core.service.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import st.alexandra.facades.UserFacade;
import st.alexandra.facades.dto.UserData;

import javax.annotation.Resource;
import java.security.Principal;

@Component
public class UserFacadeImpl implements UserFacade {
    @Resource
    private UserService userService;

    @Resource
    private UserLevelService userLevelService;

    @Override
    public UserData getUserData(String userName) {
        User user = userService.getUserByName(userName);

        UserData userData = new UserData();
        if (user != null) {
            userLevelService.getDiscountPercentage(user.getLevel()).ifPresent(userData::setDiscountPercentage);
            userData.setLevel(user.getLevel());
            userData.setAchievements(user.getAchievements());
            userData.setXP(user.getExperiencePoints());
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
