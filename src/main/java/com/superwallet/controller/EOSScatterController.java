package com.superwallet.controller;

import com.alibaba.fastjson.JSONObject;
import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.MessageRepresentation;
import com.superwallet.common.SuperResult;
import com.superwallet.response.ResponseEOSScatterCPUNETEntry;
import com.superwallet.service.EOSScatterService;
import com.superwallet.service.TokenService;
import com.superwallet.utils.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * EOS游戏模块
 */
@Controller
public class EOSScatterController {
    @Autowired
    private EOSScatterService eosScatterService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JedisClient jedisClient;

    @RequestMapping(value = "/eos/scatter/getOrRequestIdentity", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult getOrRequestIdentity(HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT, null);
        SuperResult result = eosScatterService.getOrRequestIdentity(UID);
        return result;
    }

    @RequestMapping(value = "/eos/scatter/identityFromPermissions", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult identityFromPermissions(HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT, null);
        SuperResult result = eosScatterService.identityFromPermissions(UID);
        return result;
    }

    @RequestMapping(value = "/eos/scatter/requestSignature", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult requestSignature(String buf, HttpServletRequest request) {
//        JSONObject json = JSONObject.parseObject(buf);
//        String out = json.getString("buf");
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT, null);
        int restTimes;
        try {
            restTimes = Integer.parseInt(jedisClient.get(CodeRepresentation.REDIS_PRE_FREETIMES + UID));
        } catch (Exception e) {
            restTimes = 0;
        }
        if (restTimes > 3) {
            restTimes = 3;
            jedisClient.set(CodeRepresentation.REDIS_PRE_FREETIMES, "3");
        } else if (restTimes <= 0) {
            restTimes = 0;
        }
        jedisClient.decr(CodeRepresentation.REDIS_PRE_FREETIMES + UID);
        SuperResult result;
        try {
            result = eosScatterService.requestSignature(UID, buf, restTimes);
        } catch (Exception e) {
            jedisClient.incr(CodeRepresentation.REDIS_PRE_FREETIMES + UID);
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, "请求失败!", null);
        }
        //如果失败要加回剩余次数
        if (result.getCode() == CodeRepresentation.CODE_SUCCESS && result.getStatus() == CodeRepresentation.STATUS_1) {
            jedisClient.incr(CodeRepresentation.REDIS_PRE_FREETIMES + UID);
        }
        return result;
    }

    @RequestMapping(value = "/eos/scatter/getOriginData", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult getOriginData(String data, HttpServletRequest request) {
//        JSONObject json = JSONObject.parseObject(data);
//        String str = json.getString("data");
//        System.out.println(str);
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT, null);
        SuperResult result = eosScatterService.getOriginData(data);
        return result;
    }

    @RequestMapping(value = "/eos/getResouceInfo", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult getEOSCPUNETInfo(HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT, null);
        ResponseEOSScatterCPUNETEntry info = eosScatterService.getCPUNETPercent(UID);
        return SuperResult.ok(info);
    }

}
