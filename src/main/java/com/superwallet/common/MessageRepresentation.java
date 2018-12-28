package com.superwallet.common;

import java.io.Serializable;

public class MessageRepresentation implements Serializable {
    //所有的返回message
    //----------注册登录模块开始------------
    //1.获取验证码
    public static final String REG_GETIDCODE_CODE_0_STATUS_0 = "该手机号已经被注册";
    //2.验证码-邀请码提交确认
    public static final String REG_REGCONFIRM_CODE_0_STATUS_0 = "验证码错误";
    public static final String REG_REGCONFIRM_CODE_0_STATUS_1 = "邀请码错误";
    public static final String REG_REGCONFIRM_CODE_0_STATUS_2 = "短信验证码发送失败";
    public static final String REG_REGCONFIRM_CODE_1_STATUS_0 = "请求成功";
    //3.注册
    public static final String REG_REG_CODE_0_STATUS_0 = "用户已注册";
    public static final String REG_REG_CODE_1_STATUS_0 = "用户注册成功";
    //4.实名认证
    public static final String LOGIN_VERIFYUSER_CODE_1_STATUS_0 = "实名认证登记成功";
    //5.密码登录
    public static final String LOGIN_LOGINBYPASSWORD_CODE_0_STATUS_0 = "密码错误";
    public static final String LOGIN_LOGINBYPASSWORD_CODE_0_STATUS_1 = "无此手机号";
    public static final String LOGIN_LOGINBYPASSWORD_CODE_1_STATUS_0 = "登录成功";
    //6.手机验证码登录--获取手机验证码
    public static final String LOGIN_GETIDCODE_CODE_0_STATUS_0 = "手机号码未注册";
    public static final String LOGIN_GETIDCODE_CODE_0_STATUS_1 = "短信验证码发送失败";
    public static final String LOGIN_GETIDCODE_CODE_1_STATUS_0 = "短信验证码发送成功";
    //7.验证码登录
    public static final String LOGIN_LOGINBYCODE_CODE_1_STATUS_0 = "登录成功";
    //8.找回密码
    public static final String LOGIN_FINDPASSWORD_CODE_0_STATUS_1 = "无此手机号";
    public static final String LOGIN_FINDPASSWORD_CODE_1_STATUS_0 = "修改成功";
    //9.设置支付密码
    public static final String LOGIN_SETPAYCODE_CODE_1_STATUS_0 = "设置支付密码成功";
    //10.支付密码验证
    public static final String LOGIN_PAYCODEVALIDATION_CODE_0_STATUS_0 = "支付密码错误";
    public static final String LOGIN_PAYCODEVALIDATION_CODE_1_STATUS_0 = "支付密码正确";
    //11.修改密码
    public static final String LOGIN_CHANGEPASSWORD_CODE_0_STATUS_2 = "旧密码错误";
    public static final String LOGIN_CHANGEPASSWORD_CODE_1_STATUS_0 = "修改密码成功";
    //12.修改用户基本信息
    public static final String LOGIN_MODIFYUSERBASIC_CODE_1_STATUS_0 = "修改用户基本信息成功";
    //13.登出
    public static final String LOGIN_LOGOUT_CODE_1_STATUS_0 = "登出成功";
    //14.支付密码是否存在
    public static final String LOGIN_PAYCODEEXISTS_CODE_0_STATUS_0 = "支付密码不存在";
    public static final String LOGIN_PAYCODEEXISTS_CODE_1_STATUS_0 = "支付密码存在";
    //----------注册登录模块结束-----------------
    //----------用户基本信息模块开始--------------
    //1.用户基本信息
    public static final String USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT = "登录超时";
    public static final String USER_USER_CODE_1_STATUS_0 = "用户基本信息返回成功";
    //2.用户作为邀请人的信息
    public static final String SUCCESS_CODE_1_STATUS_0 = "请求成功";
    //----------用户基本信息模块结束-------------
    //----------中心钱包模块开始-------------
    //1.中心钱包转入---待定
    //2.中心钱包用户提币请求
    public static final String CWALLET_WITHDRAWREQUEST_CODE_0_STATUS_0 = "余额不足";
    public static final String CWALLET_WITHDRAWREQUEST_CODE_1_STATUS_0 = "提币请求成功";
    //3.用户提币确认
    public static final String CWALLET_WITHDRAW_CODE_0_STATUS_0 = "提币确认失败";
    public static final String CWALLET_WITHDRAW_CODE_1_STATUS_0 = "提币确认成功";
    //4.购买代理人
    public static final String CWALLET_BUYAGENT_CODE_0_STATUS_0 = "余额不足";
    //----------中心钱包模块结束-------------
    //----------链上钱包模块开始-------------
    //1.展示链上钱包信息
    //2.链上钱包转账
    public static final String DWALLET_TRANSFER_CODE_0_STATUS_0 = "余额不足";
    public static final String DWALLET_TRANSFER_CODE_1_STATUS_0 = "转账请求成功";
    //3.锁仓
    public static final String DWALLET_LOCK_CODE_0_STATUS_0 = "锁仓失败";
    public static final String DWALLET_LOCK_CODE_1_STATUS_0 = "锁仓成功";

    //系统error时返回信息
    public static final String ERROR_MSG = "对不起！系统正在进行维护";
}
