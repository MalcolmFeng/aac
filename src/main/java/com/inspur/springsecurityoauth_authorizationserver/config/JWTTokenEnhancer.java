package com.inspur.springsecurityoauth_authorizationserver.config;

import com.alibaba.fastjson.JSON;
import com.inspur.springsecurityoauth_authorizationserver.data.SysRole;
import com.inspur.springsecurityoauth_authorizationserver.data.SysUser;
import com.inspur.springsecurityoauth_authorizationserver.service.UserService;
import com.inspur.springsecurityoauth_authorizationserver.util.SpringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JWTTokenEnhancer implements TokenEnhancer {

    @Value("${manage.server}")
    private String manageServer;

    @Value("${jwt.license}")
    private String license;
    /**
     * request里只有code
     * @param oAuth2AccessToken
     * @param oAuth2Authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        // 设置token的有效期120分钟
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 120);
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setExpiration(nowTime.getTime());

//        查询用户信息和权限
        User user = (User) oAuth2Authentication.getUserAuthentication().getPrincipal();
        UserService userService = SpringUtils.getBean(UserService.class);
        SysUser sysUser = userService.getSysUser(user.getUsername());
        List<SysRole> roles = userService.selectSysRolesByUserId(sysUser.getUserId());
        Set<Long> rolesSet = new HashSet<>();
        for (SysRole role : roles){
            rolesSet.add(role.getRoleId());
        }
        // 设置用户信息：所属client、角色、签名证书； 设置管理后台的域名；
        Map<String, Object> additionalInfomationMap = new HashMap<>();
        additionalInfomationMap.put("clients",sysUser.getClientId());
        additionalInfomationMap.put("userId",sysUser.getUserId());
        additionalInfomationMap.put("loginName",sysUser.getLoginName());
        additionalInfomationMap.put("rolesSet",JSON.toJSONString(rolesSet));
        additionalInfomationMap.put("license", license);
        additionalInfomationMap.put("manage.server",manageServer);
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfomationMap);
        return oAuth2AccessToken;
    }
}
