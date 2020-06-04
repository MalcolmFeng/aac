package com.inspur.springsecurityoauth_authorizationserver.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.inspur.mysdk.IUserInfo;
import com.inspur.springsecurityoauth_authorizationserver.data.SysRole;
import com.inspur.springsecurityoauth_authorizationserver.data.SysUser;
import com.inspur.springsecurityoauth_authorizationserver.service.UserService;
import com.inspur.springsecurityoauth_authorizationserver.util.JWTUtils;
import com.inspur.springsecurityoauth_authorizationserver.util.ServletUtils;
import com.inspur.springsecurityoauth_authorizationserver.util.SpringUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class JWTTokenEnhancer implements TokenEnhancer {

    /**
     * request里只有code
     * @param oAuth2AccessToken
     * @param oAuth2Authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        // 设置证书
        Map<String, Object> additionalInfomationMap = new HashMap<>();
        additionalInfomationMap.put("license", "inspurhealth");

        // 设置token的有效期120分钟
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 120);
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setExpiration(nowTime.getTime());

//        查询用户信息和权限
        User user = (User) oAuth2Authentication.getUserAuthentication().getPrincipal();
        UserService userService = SpringUtils.getBean(UserService.class);
        SysUser sysUser = userService.getSysUser(user.getUsername());
        List<SysRole> roles = userService.selectSysRolesByUserId(sysUser.getUserId());
        Set<String> rolesSet = new HashSet<>();
        for (SysRole role : roles){
            rolesSet.add(role.getRoleKey());
        }
        additionalInfomationMap.put("rolesSet",JSON.toJSONString(rolesSet));
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfomationMap);
        return oAuth2AccessToken;
    }
}
