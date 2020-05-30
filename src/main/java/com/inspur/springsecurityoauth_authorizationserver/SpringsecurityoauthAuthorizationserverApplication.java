package com.inspur.springsecurityoauth_authorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableAuthorizationServer
@SpringBootApplication
public class SpringsecurityoauthAuthorizationserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecurityoauthAuthorizationserverApplication.class, args);
    }

}
