package com.inspur.springsecurityoauth_authorizationserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @RequestMapping("/resource")
    public String resource(){
        return "xxx";
    }
}
