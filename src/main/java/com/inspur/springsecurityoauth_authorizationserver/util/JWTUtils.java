package com.inspur.springsecurityoauth_authorizationserver.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

public class JWTUtils {

    /**
     * 在jwt中解析userName
     * @param jwt
     * @return
     */
    public static String getUserNameByJWT(String jwt){
        String[] jwts = jwt.split("\\.");
        String headerJson = StringUtils.newStringUtf8(Base64.decodeBase64(jwts[0]));
        String payloadJson = StringUtils.newStringUtf8(Base64.decodeBase64(jwts[1]));
        JSONObject payload = JSON.parseObject(payloadJson);
        String username = payload.getString("user_name");
        return username;
    }
}
