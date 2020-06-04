package com.inspur.springsecurityoauth_authorizationserver.test;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 基本的验证方式
 *
 * @author chen.nie
 * @date 2018/6/12
 **/
public class UserJoinTimeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public UserJoinTimeAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 认证授权，如果jointime在当前时间之后则认证通过
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//        if (!(userDetails instanceof Student)) {
//            return null;
//        }
//        Student student = (Student) userDetails;
//        if (student.getJoinTime().after(new Date()))
            return new UserJoinTimeAuthenticationToken(username);
//        return null;
    }

    /**
     * 只处理UserJoinTimeAuthentication的认证
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.getName().equals(UserJoinTimeAuthenticationToken.class.getName());
    }
}
