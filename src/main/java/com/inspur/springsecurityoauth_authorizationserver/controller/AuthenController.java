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

@RequestMapping("/auth")
@RestController
public class AuthenController {

    @Autowired
    UserService userService;

    @PostMapping("/authByJWT")
    public String authByJWT(String token,String uri){
        // 解析jwt,
        String loginName = JWTUtils.getUserNameByJWT(token);

        SysUser user = userService.getSysUser(loginName);
        List<SysRole> roles = userService.selectSysRolesByUserId(user.getUserId());

        // 查询菜单
        Set<String> urls = userService.selectUrlsByUserId(user.getUserId());

        // 判断uri是否在菜单中并构造返回信息
        JSONObject result = new JSONObject();
        Boolean authFlag = false;
        for (String url:urls){
            if (url.contains(uri)){
                authFlag = true;
                break;
            }
        }

        // 将uri转换为perm进行鉴权
        if (!authFlag){
            Set<String> permsSet = userService.selectPermsByUserId(user.getUserId());
            String thisPerms = uri.replace("/",":").substring(1);
            if (permsSet.contains(thisPerms)){
                authFlag = true;
            }
        }

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
