package com.superwallet.service;

import com.superwallet.common.LoginResult;

public interface LoginRegisterService {
    public boolean isRegistered(String phoneNum);

    public String register(String phoneNum, String passWord, String invitedCode, String rootPath);

    public boolean isValidInvitedCode(String invitedCode);

    public LoginResult loginByPassWord(String phoneNum, String passWord);

    public LoginResult loginByCode(String phoneNum);

    public LoginResult findPassword(String phoneNum, String newPassWord);
}
