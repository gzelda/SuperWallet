package com.superwallet.utils;

public class CodeGenerator {

    //创建短信验证码
    public static String smsCode() {
        String random = (int) ((Math.random() * 9 + 1) * 100000) + "";
        return random;
    }

    //生成用户UID
    public static String getUID(String phoneNum) {
        return smsCode() + phoneNum;
    }

}
