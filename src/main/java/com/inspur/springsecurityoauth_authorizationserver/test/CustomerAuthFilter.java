package com.inspur.springsecurityoauth_authorizationserver.test;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义过滤器
 */
public class CustomerAuthFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationManager authenticationManager;

    public CustomerAuthFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/login", "POST"));
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter("username");
        UserJoinTimeAuthenticationToken usernamePasswordAuthenticationToken =new UserJoinTimeAuthenticationToken(username);
        Authentication authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (authentication != null) {
            super.setContinueChainBeforeSuccessfulAuthentication(true);
        }
        return authentication;
    }
}
