package st.alexandra.facades;

import st.alexandra.facades.dto.UserData;

import java.security.Principal;

public interface UserFacade {
    UserData getUserData(String userName);
    UserData getUserData(Principal principal);
//    UserData getUserLevelData(String userName);
//    UserData getUserLevelData(Principal principal);
//
//    UserData getUserLevelData(User user);
}
