package com.inspur.springsecurityoauth_authorizationserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.inspur.springsecurityoauth_authorizationserver.data.SysRole;
import com.inspur.springsecurityoauth_authorizationserver.data.SysUser;
import com.inspur.springsecurityoauth_authorizationserver.service.UserService;
import com.inspur.springsecurityoauth_authorizationserver.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 鉴权接口
 */
@RequestMapping("/auth")
@RestController
public class AuthenController {

    @Autowired
    UserService userService;

    @PostMapping("/authByJWT")
    public String authByJWT(String token,String uri){
        // 解析JWT获取登录用户
        String loginName = JWTUtils.getUserNameByJWT(token);
        SysUser user = userService.getSysUser(loginName);

        // 查询权限中的第三方url并鉴权
        Set<String> urls = userService.selectUrlsByUserId(user.getUserId());
        JSONObject result = new JSONObject();
        Boolean authFlag = false;
        for (String url:urls){
            if (url.contains(uri)){
                authFlag = true;
                break;
            }
        }

        // 查询权限中的perms并鉴权
        if (!authFlag){
            Set<String> permsSet = userService.selectPermsByUserId(user.getUserId());
            String thisPerms = uri.replace("/",":").substring(1);
            if (permsSet.contains(thisPerms)){
                authFlag = true;
            }
        }

        // 响应鉴权结果
        if (authFlag){
            result.put("code",200);
            result.put("message","access!");
        }else{
            result.put("code",403);
            result.put("message","access denied!");
        }
        return result.toJSONString();
    }
}
