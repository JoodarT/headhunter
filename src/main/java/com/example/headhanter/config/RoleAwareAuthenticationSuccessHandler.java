package com.example.headhanter.config;

import com.example.headhanter.models.RoleEntity;
import com.example.headhanter.models.User;
import com.example.headhanter.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RoleAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        User user = userService.getUserByEmail(authentication.getName());
        RoleEntity role = user.getRole();
        String actualRole = role != null ? role.getRole() : null;

        String requestedRole = request.getParameter("role");
        if (requestedRole != null && !requestedRole.isBlank() && !requestedRole.equalsIgnoreCase(actualRole)) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            response.sendRedirect("/login?roleMismatch");
            return;
        }

        if ("EMPLOYER".equals(actualRole)) {
            response.sendRedirect("/resumes");
        } else if ("APPLICANT".equals(actualRole)) {
            response.sendRedirect("/vacancies");
        } else {
            response.sendRedirect("/profile");
        }
    }
}
