package com.superwallet.service;

import com.superwallet.common.LoginResult;
import com.superwallet.common.SuperResult;

public interface LoginRegisterService {
    public boolean isRegistered(String phoneNum);

    public SuperResult register(String phoneNum, String passWord, String invitedCode);

    public boolean isValidInvitedCode(String invitedCode);

    public LoginResult loginByPassWord(String phoneNum, String passWord);

    public LoginResult loginByCode(String phoneNum);

    public LoginResult findPassword(String phoneNum, String newPassWord);

    public boolean setPayCode(String UID, String payCode);

    public boolean payCodeValidation(String UID, String payCode);

    public boolean initWallet(String UID);

    public boolean isValidOldPassword(String UID, String oldPassWord);

    public boolean modifyUserBasic(String UID, String headPhoto, String nickName, Byte sex, String rootPath);

    public boolean isPayCodeExists(String UID);

    public boolean verifyUser(String UID, String IDCardNumber, String realName, String IDCardFront, String IDCardBack, String face);
}
