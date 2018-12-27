package com.superwallet.controller;

import com.superwallet.common.MessageRepresentation;
import com.superwallet.common.SuperResult;
import com.superwallet.response.ResponseUserAgentInfo;
import com.superwallet.response.ResponseUserInvitingInfo;
import com.superwallet.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 每次用户请求之前先请求查看用户登录是否过期
 */
@Controller
public class TokenController {
    @Autowired
    private TokenService tokenService;

    /**
     * 获取用户基本信息
     *
     * @param token
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/token/{token}")
    @ResponseBody
    public SuperResult getUserByToken(@PathVariable String token, HttpServletRequest request) {
        SuperResult result = tokenService.getUserByToken(token, request);
        return result;
    }

    /**
     * 获取用户作为邀请人的信息
     *
     * @param UID
     * @return
     */
    @RequestMapping(value = "/user/invitingInfo", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult getInvitingInfo(String UID) {
        ResponseUserInvitingInfo result = tokenService.getInvitingInfo(UID);
        SuperResult out = SuperResult.ok(result);
        out.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return out;
    }

    /**
     * 获取用户作为代理人的信息
     *
     * @param UID
     * @return
     */
    @RequestMapping(value = "/user/agentInfo", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult getAgentInfo(String UID) {
        ResponseUserAgentInfo result = tokenService.getAgentInfo(UID);
        SuperResult out = SuperResult.ok(result);
        out.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return out;
    }

}
