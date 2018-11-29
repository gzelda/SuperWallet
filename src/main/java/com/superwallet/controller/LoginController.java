package com.superwallet.controller;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.LoginResult;
import com.superwallet.common.SuperResult;
import com.superwallet.service.LoginRegisterService;
import com.superwallet.service.PhoneMessageService;
import com.superwallet.utils.CodeGenerator;
import com.superwallet.utils.SHA1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class LoginController {

    //获取手机验证码Service
    @Autowired
    private PhoneMessageService phoneMessageService;

    //注册登录Service
    @Autowired
    private LoginRegisterService loginRegisterService;

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
            return result;
        }
        //缓存，用来后来验证验证码是否正确
        HttpSession session = request.getSession();
        String phoneCode = CodeGenerator.smsCode();
        session.setAttribute(phoneNum, phoneCode);
        session.setMaxInactiveInterval(60);
        int code = phoneMessageService.sendMessage(phoneNum, phoneCode);
        result = new SuperResult(code, CodeRepresentation.STATUS_0, null);
        return result;
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
        SuperResult result;
        //判断验证码是否正确
        HttpSession session = request.getSession();
        String phoneCode = (String) session.getAttribute(phoneNum);
        if (phoneCode == null || phoneCode.equals("") || !phoneIDCode.equals(phoneCode)) {
            result = new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
            return result;
        }
        //判断邀请码是否正确
        boolean isValidInvitedCode = loginRegisterService.isValidInvitedCode(invitedCode);
        if (!isValidInvitedCode) {
            result = new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, null);
            return result;
        }
        result = SuperResult.ok();
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
    public SuperResult register(String phoneNum, String passWord, String invitedCode, HttpServletRequest request) {
        SuperResult result;
        //获取项目路径
        String headPhotoPath = request.getSession().getServletContext().getRealPath("/");
        //加密密码
        passWord = SHA1.encode(passWord);
        //注册
        String uid = loginRegisterService.register(phoneNum, passWord, invitedCode, headPhotoPath);
        if (uid.equals("registered")) {
            result = new SuperResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, null);
        }
        HashMap<String, String> map = new HashMap();
        map.put("UID", uid);
        return SuperResult.ok(map);
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
    public SuperResult loginByPassWord(String phoneNum, String passWord) {
        passWord = SHA1.encode(passWord);
        LoginResult loginResult = loginRegisterService.loginByPassWord(phoneNum, passWord);
        //当登录成功时传UID
        if (loginResult.getCode() == 1) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(CodeRepresentation.UID, loginResult.getUid());
            return new SuperResult(loginResult.getCode(), loginResult.getStatus(), map);
        }
        //其他任何失败情况不传UID
        return new SuperResult(loginResult.getCode(), loginResult.getStatus(), null);
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
            result = new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
            return result;
        }
        //缓存，用来后来验证验证码是否正确
        HttpSession session = request.getSession();
        String phoneCode = CodeGenerator.smsCode();
        session.setAttribute(phoneNum, phoneCode);
        session.setMaxInactiveInterval(60);
        int code = phoneMessageService.sendMessage(phoneNum, phoneCode);
        result = new SuperResult(code, CodeRepresentation.STATUS_0, null);
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
    public SuperResult loginByCode(String phoneNum, String phoneIDCode, HttpServletRequest request) {
        SuperResult result;
        //判断手机验证码是否正确
        HttpSession session = request.getSession();
        String phoneCode = (String) session.getAttribute(phoneNum);
        //验证码错误
        if (phoneCode == null || phoneCode.equals("") || !phoneCode.equals(phoneIDCode)) {
            result = new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
            return result;
        }
        LoginResult loginResult = loginRegisterService.loginByCode(phoneNum);
        //当登录成功时传UID
        if (loginResult.getCode() == 1) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(CodeRepresentation.UID, loginResult.getUid());
            return new SuperResult(loginResult.getCode(), loginResult.getStatus(), map);
        }
        //其他任何失败情况不传UID
        return new SuperResult(loginResult.getCode(), loginResult.getStatus(), null);
    }

    @RequestMapping(value = "/login/findPassword", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult findPassWord(String phoneNum, String phoneIDCode, String newPassWord, HttpServletRequest request) {
        SuperResult result;
        //判断验证码是否正确
        HttpSession session = request.getSession();
        String phoneCode = (String) session.getAttribute(phoneNum);
        //验证码错误
        if (phoneCode == null || phoneCode.equals("") || !phoneCode.equals(phoneIDCode)) {
            result = new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, null);
            return result;
        }
        //对密码进行加密
        newPassWord = SHA1.encode(newPassWord);
        LoginResult loginResult = loginRegisterService.findPassword(phoneNum, newPassWord);
        //当登录成功时传UID
        if (loginResult.getCode() == 1) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(CodeRepresentation.UID, loginResult.getUid());
            return new SuperResult(loginResult.getCode(), loginResult.getStatus(), map);
        }
        //其他任何失败情况不传UID
        return new SuperResult(loginResult.getCode(), loginResult.getStatus(), null);
    }

}
