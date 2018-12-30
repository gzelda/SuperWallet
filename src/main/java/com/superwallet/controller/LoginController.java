package com.superwallet.controller;

import com.superwallet.common.*;
import com.superwallet.pojo.Userbasic;
import com.superwallet.service.LoginRegisterService;
import com.superwallet.service.PhoneMessageService;
import com.superwallet.service.TokenService;
import com.superwallet.utils.CodeGenerator;
import com.superwallet.utils.JedisClient;
import com.superwallet.utils.SHA1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    //获取手机验证码Service
    @Autowired
    private PhoneMessageService phoneMessageService;

    //注册登录Service
    @Autowired
    private LoginRegisterService loginRegisterService;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TokenService tokenService;

    /**
     * 注册时获取手机验证码
     *
     * @param phoneNum
     * @param request
     * @return
     */
    @RequestMapping(value = "/register/getIDCode", method = RequestMethod.GET)
    @ResponseBody
    public SuperResult registerSendMessage(String phoneNum, HttpServletRequest request) {
        //判断手机号是否被注册
        boolean registered = loginRegisterService.isRegistered(phoneNum);
        SuperResult result;
        //如果被注册，返回code为0--fail，status为0--该手机号已经被注册
        if (registered) {
            result = new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
            result.setMsg(MessageRepresentation.REG_GETIDCODE_CODE_0_STATUS_0);
            return result;
        }
        //缓存，用来后来验证验证码是否正确
        HttpSession session = request.getSession();
        String phoneCode = CodeGenerator.smsCode();
        session.setAttribute(phoneNum, phoneCode);
        session.setMaxInactiveInterval(CodeRepresentation.MESSAGECODE_EXPIRE);
        int code = phoneMessageService.sendMessage(phoneNum, phoneCode);
        if (code == CodeRepresentation.CODE_FAIL) {
            return new SuperResult(code, CodeRepresentation.STATUS_2, MessageRepresentation.REG_REGCONFIRM_CODE_0_STATUS_2, null);
        }
        result = new SuperResult(code, CodeRepresentation.STATUS_0, MessageRepresentation.REG_REGCONFIRM_CODE_1_STATUS_0, null);
        return result;
    }

    /**
     * 判断手机验证码是否正确
     *
     * @param phoneNum
     * @param phoneIDCode
     * @param request
     * @return
     */
    @RequestMapping(value = "/login/isValidMessageCode", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult isValidMessageCode(String phoneNum, String phoneIDCode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute(phoneNum);
        //验证码错误
        if (code == null || code.equals("") || !phoneIDCode.equals(code)) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.REG_REGCONFIRM_CODE_0_STATUS_0, null);
        } else if (phoneIDCode.equals(code)) {//验证码正确
            return SuperResult.ok("手机验证码正确");
        }
        return new SuperResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, MessageRepresentation.ERROR_MSG, null);
    }

    /**
     * 用户注册模块
     *
     * @param phoneNum
     * @param phoneIDCode
     * @param invitedCode
     * @param request
     * @return
     */
    @RequestMapping(value = "/register/registerConfirm", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult registerConfirm(String phoneNum, String phoneIDCode, String invitedCode, HttpServletRequest request) {
        //判断验证码是否正确
        SuperResult result = isValidMessageCode(phoneNum, phoneIDCode, request);
        if (result == null || result.getCode() != CodeRepresentation.CODE_SUCCESS) return result;
        //判断邀请码是否正确
        boolean isValidInvitedCode = loginRegisterService.isValidInvitedCode(invitedCode);
        if (!isValidInvitedCode) {
            result = new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, null);
            result.setMsg(MessageRepresentation.REG_REGCONFIRM_CODE_0_STATUS_1);
            return result;
        }
        result = SuperResult.ok(MessageRepresentation.REG_REGCONFIRM_CODE_1_STATUS_0);
        return result;
    }

    /**
     * 注册入库
     *
     * @param phoneNum
     * @param passWord
     * @param invitedCode
     * @return
     */
    @RequestMapping(value = "/register/register", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult register(final String phoneNum, String passWord, String invitedCode, HttpServletRequest request, HttpServletResponse response) {
        SuperResult result;
        //获取项目路径
        String headPhotoPath = request.getContextPath();
//        String headPhotoPath = request.getSession().getServletContext().getRealPath("/");
        //加密密码
        passWord = SHA1.encode(passWord);
        //注册
        result = loginRegisterService.register(phoneNum, passWord, invitedCode, headPhotoPath);
        //注册失败
        if (result.getCode() != CodeRepresentation.CODE_SUCCESS) {
            return result;
        }
        String uid = result.getData().toString();
        //注册成功后初始化钱包信息
        String sessionId = SHA1.encode(uid);
        //添加session,并设置过期时间
        jedisClient.set(CodeRepresentation.SESSIONID_PREFIX + sessionId, uid);
        jedisClient.expire(CodeRepresentation.SESSIONID_PREFIX + sessionId, CodeRepresentation.SESSION_EXPIRE);
        //写cookie
        CookieUtils.setCookie(request, response, CodeRepresentation.TOKEN_KEY, sessionId, CodeRepresentation.COOKIE_EXPIRE);
        result = SuperResult.ok(MessageRepresentation.REG_REG_CODE_1_STATUS_0);
        //注册或登录成功传手机号用来做推送用--绑定的唯一ID
        Map<String, String> returnKV = new HashMap<String, String>();
        returnKV.put("phoneNum", phoneNum);
        result.setData(returnKV);
        return result;
    }

    /**
     * 用户实名认证
     *
     * @param IDCardNumber
     * @param realName
     * @param IDCardFront
     * @param IDCardBack
     * @param face
     * @return
     */
    @RequestMapping(value = "/login/verifyUser", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult verifyUser(String IDCardNumber, String realName, String IDCardFront, String IDCardBack, String face, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        boolean res = loginRegisterService.verifyUser(UID, IDCardNumber, realName, IDCardFront, IDCardBack, face);
        if (!res)
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.LOGIN_VERIFYUSER_CODE_0_STATUS_0, null);
        return SuperResult.ok(MessageRepresentation.LOGIN_VERIFYUSER_CODE_1_STATUS_0);
    }

    /**
     * 密码登录
     *
     * @param phoneNum
     * @param passWord
     * @return
     */
    @RequestMapping(value = "/login/loginByPassWord", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult loginByPassWord(String phoneNum, String passWord, HttpServletRequest request, HttpServletResponse response) {
        passWord = SHA1.encode(passWord);
        LoginResult loginResult = loginRegisterService.loginByPassWord(phoneNum, passWord);
        //当登录成功时传UID
        if (loginResult.getCode() == CodeRepresentation.CODE_SUCCESS) {
            Userbasic user = loginResult.getUser();
            //添加session
            String uid = user.getUid();
            String sessionId = SHA1.encode(uid);
            jedisClient.set(CodeRepresentation.SESSIONID_PREFIX + sessionId, uid);
            jedisClient.expire(CodeRepresentation.SESSIONID_PREFIX + sessionId, CodeRepresentation.SESSION_EXPIRE);
            //写cookie
            CookieUtils.setCookie(request, response, CodeRepresentation.TOKEN_KEY, sessionId, CodeRepresentation.COOKIE_EXPIRE);
//            System.out.println("CookieID: " + CookieUtils.getCookieValue(request, CodeRepresentation.TOKEN_KEY));
            //注册或登录成功传手机号用来做推送用--绑定的唯一ID
            Map<String, String> returnKV = new HashMap<String, String>();
            returnKV.put("phoneNum", phoneNum);
            return new SuperResult(loginResult.getCode(), loginResult.getStatus(), MessageRepresentation.LOGIN_LOGINBYPASSWORD_CODE_1_STATUS_0, returnKV);
        }
        //其他任何失败情况不传UID
        return new SuperResult(loginResult.getCode(), loginResult.getStatus(), loginResult.getMsg(), null);
    }

    /**
     * 登录时获取手机验证码
     *
     * @param phoneNum
     * @param request
     * @return
     */
    @RequestMapping(value = "/login/getIDCode", method = RequestMethod.GET)
    @ResponseBody
    public SuperResult loginSendMessage(String phoneNum, HttpServletRequest request) {
        //判断手机号是否还未注册
        boolean registered = loginRegisterService.isRegistered(phoneNum);
        SuperResult result;
        //如果没注册，返回code为0--fail，status为0--该手机号未注册无法登录
        if (!registered) {
            result = new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.LOGIN_GETIDCODE_CODE_0_STATUS_0, null);
            return result;
        }
        //缓存，用来后来验证验证码是否正确
        HttpSession session = request.getSession();
        String phoneCode = CodeGenerator.smsCode();
        session.setAttribute(phoneNum, phoneCode);
        session.setMaxInactiveInterval(CodeRepresentation.MESSAGECODE_EXPIRE);
        int code = phoneMessageService.sendMessage(phoneNum, phoneCode);
        if (code == CodeRepresentation.CODE_FAIL)
            return new SuperResult(code, CodeRepresentation.STATUS_0, MessageRepresentation.LOGIN_GETIDCODE_CODE_0_STATUS_1, null);
        result = new SuperResult(code, CodeRepresentation.STATUS_0, MessageRepresentation.LOGIN_GETIDCODE_CODE_1_STATUS_0, null);
        return result;
    }


    /**
     * 验证码登录
     *
     * @param phoneNum
     * @param phoneIDCode
     * @return
     */
    @RequestMapping(value = "/login/loginByCode", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult loginByCode(String phoneNum, String phoneIDCode, HttpServletRequest request, HttpServletResponse response) {
        SuperResult result;
        //判断手机验证码是否正确
        result = isValidMessageCode(phoneNum, phoneIDCode, request);
        //如果验证码错误
        if (result.getCode() == CodeRepresentation.CODE_FAIL) return result;
        LoginResult loginResult = loginRegisterService.loginByCode(phoneNum);
        //当登录成功时传UID
        if (loginResult.getCode() == CodeRepresentation.CODE_SUCCESS) {
            //登录成功存session
            Userbasic user = loginResult.getUser();
            //添加session
            String uid = user.getUid();
            String sessionId = SHA1.encode(uid);
            jedisClient.set(CodeRepresentation.SESSIONID_PREFIX + sessionId, uid);
            jedisClient.expire(CodeRepresentation.SESSIONID_PREFIX + sessionId, CodeRepresentation.SESSION_EXPIRE);
            //写cookie
            CookieUtils.setCookie(request, response, CodeRepresentation.TOKEN_KEY, sessionId, CodeRepresentation.COOKIE_EXPIRE);
            //注册或登录成功传手机号用来做推送用--绑定的唯一ID
            Map<String, String> returnKV = new HashMap<String, String>();
            returnKV.put("phoneNum", phoneNum);
            return new SuperResult(loginResult.getCode(), loginResult.getStatus(), loginResult.getMsg(), returnKV);
        }
        //其他任何失败情况不传UID
        return new SuperResult(loginResult.getCode(), loginResult.getStatus(), loginResult.getMsg(), null);
    }

    /**
     * 找回密码
     *
     * @param phoneNum
     * @param phoneIDCode
     * @param newPassWord
     * @param request
     * @return
     */
    @RequestMapping(value = "/login/findPassword", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult findPassWord(String phoneNum, String phoneIDCode, String newPassWord, HttpServletRequest request) {
        SuperResult result;
        //判断验证码是否正确
        result = isValidMessageCode(phoneNum, phoneIDCode, request);
        if (result.getCode() == CodeRepresentation.CODE_FAIL) return result;
        //对密码进行加密
        newPassWord = SHA1.encode(newPassWord);
        LoginResult loginResult = loginRegisterService.findPassword(phoneNum, newPassWord);
        //当修改成功时候
        if (loginResult.getCode() == CodeRepresentation.CODE_SUCCESS) {
            return new SuperResult(loginResult.getCode(), loginResult.getStatus(), loginResult.getMsg(), null);
        }
        //其他任何失败情况
        return new SuperResult(loginResult.getCode(), loginResult.getStatus(), loginResult.getMsg(), null);
    }

    /**
     * 用户设置支付密码
     *
     * @param payCode
     * @return
     */
    @RequestMapping(value = "/login/setPayCode", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult setPayPassword(String payCode, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        //加密支付密码
        payCode = SHA1.encode(payCode);
        //更新userBasic表
        boolean res = loginRegisterService.setPayCode(UID, payCode);
        if (!res)
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.LOGIN_SETPAYCODE_CODE_0_STATUS_0, null);
        return SuperResult.ok(MessageRepresentation.LOGIN_SETPAYCODE_CODE_1_STATUS_0);
    }

    /**
     * 判断旧支付密码
     *
     * @param payCode
     * @return
     */
    @RequestMapping(value = "/login/payCodeValidation", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult payCodeValidation(String payCode, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        payCode = SHA1.encode(payCode);
        //判断旧支付密码
        boolean res = loginRegisterService.payCodeValidation(UID, payCode);
        if (!res)
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.LOGIN_PAYCODEVALIDATION_CODE_0_STATUS_0, null);
        return SuperResult.ok(MessageRepresentation.LOGIN_PAYCODEVALIDATION_CODE_1_STATUS_0);
    }

    /**
     * 修改密码
     *
     * @param phoneNum
     * @param oldPassWord
     * @param newPassWord
     * @return
     */
    @RequestMapping(value = "/login/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult changePassword(String phoneNum, String phoneIDCode, String oldPassWord, String newPassWord, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        SuperResult result;
        result = isValidMessageCode(phoneNum, phoneIDCode, request);
        if (result.getCode() == CodeRepresentation.CODE_FAIL) return result;
        //旧密码错误
        oldPassWord = SHA1.encode(oldPassWord);
        boolean validOldPassword = loginRegisterService.isValidOldPassword(UID, oldPassWord);
        if (!validOldPassword) {
            result = new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_2, MessageRepresentation.LOGIN_CHANGEPASSWORD_CODE_0_STATUS_2, null);
            return result;
        }
        //对新密码进行加密
        newPassWord = SHA1.encode(newPassWord);
        LoginResult loginResult = loginRegisterService.findPassword(phoneNum, newPassWord);
        //当修改成功时候
        if (loginResult.getCode() == CodeRepresentation.CODE_SUCCESS) {
            return new SuperResult(loginResult.getCode(), loginResult.getStatus(), MessageRepresentation.LOGIN_CHANGEPASSWORD_CODE_1_STATUS_0, null);
        }
        //其他任何失败情况
        return new SuperResult(loginResult.getCode(), loginResult.getStatus(), loginResult.getMsg(), null);
    }

    /**
     * 修改用户基本信息
     *
     * @param headPhoto
     * @param nickName
     * @param sex
     * @return
     */
    @RequestMapping(value = "/login/modifyUserBasic", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult modifyUserBasic(byte[] headPhoto, String nickName, Byte sex, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        boolean res = loginRegisterService.modifyUserBasic(UID, headPhoto, nickName, sex);
        if (!res)
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.LOGIN_MODIFYUSERBASIC_CODE_0_STATUS_0, null);
        return SuperResult.ok(MessageRepresentation.LOGIN_MODIFYUSERBASIC_CODE_1_STATUS_0);
    }

    /**
     * 登出
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/login/logout", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult logout(HttpServletRequest request, HttpServletResponse response) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        //清除session和cookie
        String sessionId = SHA1.encode(UID);
        jedisClient.del(sessionId);
        CookieUtils.deleteCookie(request, response, CodeRepresentation.TOKEN_KEY);
        return SuperResult.ok(MessageRepresentation.LOGIN_LOGOUT_CODE_1_STATUS_0);
    }

    /**
     * 判断支付密码是否存在
     *
     * @return
     */
    @RequestMapping(value = "/login/isPayCodeExists", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult isPayCodeExists(HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        boolean exists = loginRegisterService.isPayCodeExists(UID);
        SuperResult result = new SuperResult();
        if (!exists) {
            result.setCode(CodeRepresentation.CODE_FAIL);
            result.setStatus(CodeRepresentation.STATUS_0);
            result.setMsg(MessageRepresentation.LOGIN_PAYCODEEXISTS_CODE_0_STATUS_0);
        } else {
            result = SuperResult.ok(MessageRepresentation.LOGIN_PAYCODEEXISTS_CODE_1_STATUS_0);
        }
        return result;
    }

}
