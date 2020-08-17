package com.inspur.springsecurityoauth_authorizationserver.controller;


import com.inspur.springsecurityoauth_authorizationserver.data.SysUser;
import com.inspur.springsecurityoauth_authorizationserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/check_client")
    public boolean checkClient(@RequestParam("clientId") String clientId, @RequestParam("loginName") String loginName, HttpServletRequest request){

        String authorization = request.getHeader("Authorization");

        byte[] decode = Base64.getUrlDecoder().decode(authorization.split(" ")[1].getBytes());
        String clientIdAuth = new String(decode).split(":")[0];

        if (clientIdAuth.equals(clientId)){
            SysUser user = userService.getSysUser(loginName);
            if (user.getClientId().equals(clientId)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

}
