package st.alexandra.facades;

import com.es.core.model.order.Order;
import st.alexandra.facades.dto.UserData;

import java.security.Principal;
import java.util.List;

public interface UserFacade {
    UserData getUserData(String userName);
    UserData getUserData(Principal principal);

    List<Order> getUserOrders(String userName);
//    UserData getUserLevelData(String userName);
//    UserData getUserLevelData(Principal principal);
//
//    UserData getUserLevelData(User user);
}
