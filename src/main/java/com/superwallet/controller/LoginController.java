package com.superwallet.controller;

import com.superwallet.common.SuperResult;
import com.superwallet.service.PhoneMessageService;
import com.superwallet.utils.CodeGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class LoginController {

    @Resource
    private PhoneMessageService phoneMessageService;

    String phoneCode;

    @RequestMapping("/user/getIDCode")
    @ResponseBody
    public SuperResult sendMessage(String phoneNum) {
        phoneCode = CodeGenerator.smsCode();
        int code = phoneMessageService.sendMessage(phoneNum, phoneCode);
        SuperResult result = new SuperResult(code, -1, phoneCode);
        return result;
    }

}
