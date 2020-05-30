package com.inspur.springsecurityoauth_authorizationserver.controller;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 授权模块
 * @Author Malcolm
 */
@Controller
@SessionAttributes("authorizationRequest")
public class GrantController {

    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model) throws Exception {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView view = new ModelAndView("base-grant");
        view.addObject("clientId", authorizationRequest.getClientId());
        return view;
    }
}
