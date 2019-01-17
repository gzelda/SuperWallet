package com.superwallet.service.impl;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.CookieUtils;
import com.superwallet.common.MessageRepresentation;
import com.superwallet.common.SuperResult;
import com.superwallet.mapper.OptconfMapper;
import com.superwallet.mapper.UserbasicMapper;
import com.superwallet.pojo.Userbasic;
import com.superwallet.response.ResponseUserAgentInfo;
import com.superwallet.response.ResponseUserInvitingInfo;
import com.superwallet.service.CommonService;
import com.superwallet.service.TokenService;
import com.superwallet.utils.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private UserbasicMapper userbasicMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private OptconfMapper optconfMapper;

    /**
     * 获取用户基本信息
     *
     * @param UID
     * @return
     */
    @Override
    public SuperResult getUserBasic(String UID) {
        Userbasic user = userbasicMapper.selectByPrimaryKey(UID);
        user.setPassword(null);
        user.setPaypassword(null);
        return new SuperResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_0, MessageRepresentation.SUCCESS_CODE_1_STATUS_0, user);
    }

    /**
     * 根据request拿到用户的UID
     *
     * @param request
     * @return
     */
    @Override
    public String getUID(HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request, CodeRepresentation.TOKEN_KEY);
        String uid = jedisClient.get(CodeRepresentation.SESSIONID_PREFIX + token);
        if (uid == null || uid.equals("")) return null;
        jedisClient.expire(CodeRepresentation.SESSIONID_PREFIX + token, CodeRepresentation.SESSION_EXPIRE);
        jedisClient.set(CodeRepresentation.REDIS_PRE_LASTOP + uid, String.valueOf(System.currentTimeMillis()));
        return uid;
    }

    /**
     * 获取用户的邀请好友的详细信息
     *
     * @param UID
     * @return
     */
    @Override
    public ResponseUserInvitingInfo getInvitingInfo(String UID) {
        double PROFIT_INVITING_BGS;
        try {
            PROFIT_INVITING_BGS = Double.parseDouble(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_PROFIT_INVITING_BGS));
        } catch (Exception e) {
            PROFIT_INVITING_BGS = Double.parseDouble(optconfMapper.selectByPrimaryKey(CodeRepresentation.REDIS_PROFIT_INVITING_BGS).getConfvalue());
        }
        Userbasic user = userbasicMapper.selectByPrimaryKey(UID);
        String invitedCode = user.getInvitedcode();
        int hasInvitedPeopleCount = commonService.getInvitingCount(UID);
        double bgsHasGot = hasInvitedPeopleCount * PROFIT_INVITING_BGS;
        //TODO 邀请链接
        String inviteUrl = invitedCode;
        ResponseUserInvitingInfo result = new ResponseUserInvitingInfo(
                invitedCode,
                hasInvitedPeopleCount,
                bgsHasGot,
                inviteUrl
        );
        return result;
    }

    /**
     * 获取用户的代理详细信息
     *
     * @param UID
     * @return
     */
    @Override
    public ResponseUserAgentInfo getAgentInfo(String UID) {
        //总收益
        double totalProfit = 0;
        //获取下级人数
        int lowerAmount = commonService.getAgentInvitingCount(UID);
        //TODO 昨日活跃度
        int yesterdayActiveCount = 0;
        //TODO 昨日赚取收益
        double yesterdayProfit = 0;
        //总代理人数
        int totalAgentAmount = commonService.getAgentCount();
        ResponseUserAgentInfo result = new ResponseUserAgentInfo(
                totalProfit,
                lowerAmount,
                yesterdayActiveCount,
                yesterdayProfit,
                totalAgentAmount
        );
        return result;
    }
}
