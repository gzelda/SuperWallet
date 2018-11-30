package com.superwallet.controller;

import com.superwallet.common.SuperResult;
import com.superwallet.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 每次用户请求之前先请求查看用户登录是否过期
 */
@Controller
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/user/token/{token}")
    @ResponseBody
    public SuperResult getUserByToken(@PathVariable String token, HttpServletRequest request) {
        SuperResult result = tokenService.getUserByToken(token, request);
        return result;
    }

}
