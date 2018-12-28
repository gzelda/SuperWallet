package com.superwallet.service;

import com.superwallet.common.LoginResult;

public interface LoginRegisterService {
    public boolean isRegistered(String phoneNum);

    public String register(String phoneNum, String passWord, String invitedCode, String rootPath);

    public boolean isValidInvitedCode(String invitedCode);

    public LoginResult loginByPassWord(String phoneNum, String passWord);

    public LoginResult loginByCode(String phoneNum);

    public LoginResult findPassword(String phoneNum, String newPassWord);

    public void setPayCode(String UID, String payCode);

    public boolean payCodeValidation(String UID, String payCode);

    public boolean initWallet(String UID);

    public boolean isValidOldPassword(String UID, String oldPassWord);

    public void modifyUserBasic(String UID, byte[] headPhoto, String nickName, Byte sex);

    public boolean isPayCodeExists(String UID);

    public void verifyUser(String UID, String IDCardNumber, String realName, String IDCardFront, String IDCardBack, String face);
}
