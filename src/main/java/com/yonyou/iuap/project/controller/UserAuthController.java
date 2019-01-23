package com.yonyou.iuap.project.controller;

import com.yonyou.iuap.project.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户目录权限控制
 */
@RestController
@RequestMapping(value = "/auth")
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    @RequestMapping(value = "/menus")
    public @ResponseBody
    Map<String, Object> menus(HttpServletRequest request, String userCode) {
        return userAuthService.getAuthMenusByCache(userCode);
    }

}
