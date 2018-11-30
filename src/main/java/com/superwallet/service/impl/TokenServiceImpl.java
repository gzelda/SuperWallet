package com.superwallet.service.impl;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.SuperResult;
import com.superwallet.pojo.Userbasic;
import com.superwallet.service.TokenService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class TokenServiceImpl implements TokenService {

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
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
        }
        //若没过期 更新session时间
        session.setAttribute(user.getUid(), user);
        session.setMaxInactiveInterval(CodeRepresentation.SESSION_EXPIRE);
        return SuperResult.ok(user);
    }
}
