package com.inspur.springsecurityoauth_authorizationserver.service;

import com.alibaba.fastjson.JSON;
import com.inspur.springsecurityoauth_authorizationserver.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.expression.Lists;

import java.util.*;

public class SmsServiceImpl implements SmsService {

    @Autowired
    RestTemplate restTemplate;

    private static String smsAppId = "";
    private static String smsAppKey = "";


    @Override
    public void sendMessage() {
//        B2BRequest<SmsParam> b2BRequest = new B2BRequest();
//        SmsParam smsParam = new SmsParam();
//        List<String> mobiles = Lists.newArrayList();
//        mobiles.add(tel);
//
//        List<String> params = new ArrayList<>();
//        params.add(name);
//        params.add(hospital);
//        params.add(dept);
//        params.add(doctor);
//        params.add(time);
//        b2BRequest.setAppId(smsAppId);
//        smsParam.setTempId(1000002);
//        smsParam.setMobiles(mobiles);
//        smsParam.setParams(params);
//        b2BRequest.setContent(smsParam);
//        String once = RandomStringUtils.randomAlphanumeric(10);
//        b2BRequest.setOnece(once);
//
//        String content = JSON.toJSONString(smsParam);
//        System.out.println("content:"+content);
//
//        Map<String,Object> paramsForSign = new TreeMap<>();
//        paramsForSign.put("appId",smsAppId);
//        paramsForSign.put("once",once);
//        paramsForSign.put("content",content);
//        try {
//            String sign = EncryptionUtil.sha256_mac(JSON.toJSONString(paramsForSign), smsAppKey);
//            b2BRequest.setSign(sign);
//            System.out.println("签验："+sign);
//        } catch (Exception e) {
//            System.out.println("APP SECURITY FILTER ERROR:{"+e+ "}");
//        }
//
//        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//        formData.add("APPID","APPID");
//
//        HttpHeaders authHeader = new HttpHeaders();
//        authHeader.set("Authorization","Authorization");
//        ResponseEntity responseEntity = restTemplate.exchange("http://172.22.19.132:8887/b2b/sms/send", HttpMethod.POST,new HttpEntity(formData, authHeader), Map.class, new Object[0]);
//
//        if (responseEntity.getStatusCode().value() == 200 ){
//            System.out.println(responseEntity.getBody());
//        }

    }

}
