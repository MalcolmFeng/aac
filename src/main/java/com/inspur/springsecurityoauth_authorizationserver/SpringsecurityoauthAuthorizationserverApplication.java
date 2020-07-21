package com.inspur.springsecurityoauth_authorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import java.util.Base64;

@EnableAuthorizationServer
@SpringBootApplication
public class SpringsecurityoauthAuthorizationserverApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringsecurityoauthAuthorizationserverApplication.class, args);
    }

}
