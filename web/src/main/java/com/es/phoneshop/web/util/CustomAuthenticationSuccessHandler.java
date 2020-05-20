package com.es.phoneshop.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import st.alexandra.facades.PromotionsFacade;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Value("${base.path}")
    private String basePath;

    @Value("${admin.role}")
    private String adminRole;

    @Autowired
    private PromotionsFacade promotionsFacade;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            initializeUser(user.getUsername());
            if (user.getAuthorities().stream().anyMatch(authority -> adminRole.equals(authority.getAuthority()))) {
                response.sendRedirect(basePath + "/admin/orders");
                return;
            }
        }
        response.sendRedirect(basePath + "/my-profile"); // TODO check if can send response without redirect
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    private void initializeUser(String userName) {
        if (userName != null) {
            promotionsFacade.initUser(userName);
        }
    }
}
