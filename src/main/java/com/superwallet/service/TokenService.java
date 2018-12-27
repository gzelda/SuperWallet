package com.superwallet.service;

import com.superwallet.common.SuperResult;
import com.superwallet.response.ResponseUserAgentInfo;
import com.superwallet.response.ResponseUserInvitingInfo;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    public SuperResult getUserByToken(String token, HttpServletRequest request);

    public ResponseUserInvitingInfo getInvitingInfo(String UID);

    public ResponseUserAgentInfo getAgentInfo(String UID);
}
