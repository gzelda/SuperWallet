package com.superwallet.utils;

public class CodeGenerator {

    //创建短信验证码
    public static String smsCode() {
        String random = (int) ((Math.random() * 9 + 1) * 100000) + "";
        return random;
    }

    //生成用户邀请码
    public static String getInvitedCode(String phoneNum) {
        //根据手机号码
        char mapping[] = new char[]{'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H'};
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < phoneNum.length(); i++) {
            sb.append(mapping[phoneNum.charAt(i) - '0']);
        }
        return sb.toString();
    }


}
