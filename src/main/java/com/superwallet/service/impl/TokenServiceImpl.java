package com.superwallet.service.impl;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.MessageRepresentation;
import com.superwallet.common.SuperResult;
import com.superwallet.mapper.InviterMapper;
import com.superwallet.mapper.UserbasicMapper;
import com.superwallet.pojo.Userbasic;
import com.superwallet.response.ResponseUserAgentInfo;
import com.superwallet.response.ResponseUserInvitingInfo;
import com.superwallet.service.CommonService;
import com.superwallet.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private UserbasicMapper userbasicMapper;

    @Autowired
    private InviterMapper inviterMapper;

    @Autowired
    private CommonService commonService;

    /**
     * 根据cookie当中的token获取UserBasic数据
     *
     * @param token
     * @return
     */
    @Override
    public SuperResult getUserByToken(String token, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Userbasic user = (Userbasic) session.getAttribute(token);
        //登录过期
        if (user == null) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.USER_USER_CODE_0_STATUS_0, null);
        }
        //若没过期 更新session时间
        session.setAttribute(user.getUid(), user);
        session.setMaxInactiveInterval(CodeRepresentation.SESSION_EXPIRE);
        SuperResult result = SuperResult.ok(user);
        result.setMsg(MessageRepresentation.USER_USER_CODE_1_STATUS_0);
        return result;
    }

    /**
     * 获取用户的邀请好友的详细信息
     *
     * @param UID
     * @return
     */
    @Override
    public ResponseUserInvitingInfo getInvitingInfo(String UID) {
        Userbasic user = userbasicMapper.selectByPrimaryKey(UID);
        String invitedCode = user.getInvitedcode();
        int hasInvitedPeopleCount = commonService.getInvitingCount(UID);
        double bgsHasGot = hasInvitedPeopleCount * CodeRepresentation.INVITING_BGS;
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
