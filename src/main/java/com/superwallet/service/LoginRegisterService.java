package com.superwallet.service;

import com.superwallet.common.LoginResult;

import javax.servlet.http.HttpServletRequest;

public interface LoginRegisterService {
    public boolean isRegistered(String phoneNum);

    public String register(String phoneNum, String passWord, String invitedCode, String rootPath);

    public boolean isValidInvitedCode(String invitedCode);

    public LoginResult loginByPassWord(String phoneNum, String passWord);

    public LoginResult loginByCode(String phoneNum);

    public LoginResult findPassword(String phoneNum, String newPassWord);

    public void setPayCode(String UID, String payCode);

    public boolean payCodeValidation(String UID, String payCode);

    public boolean isTimeOut(String UID, HttpServletRequest request);

    public boolean initWallet(String UID);

    public boolean isValidOldPassword(String UID, String oldPassWord);
}
