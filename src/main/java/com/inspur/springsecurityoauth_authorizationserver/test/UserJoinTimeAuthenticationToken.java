package com.inspur.springsecurityoauth_authorizationserver.test;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserJoinTimeAuthenticationToken extends AbstractAuthenticationToken {

    private String username;

    public UserJoinTimeAuthenticationToken(String username) {
        super(null);
        this.username = username;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }
}
