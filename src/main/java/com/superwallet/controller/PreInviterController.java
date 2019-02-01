package com.superwallet.controller;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.SuperResult;
import com.superwallet.service.LoginRegisterService;
import com.superwallet.service.PreInviterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 预邀请Controller
 */
@Controller
public class PreInviterController {

    @Autowired
    private PreInviterService preInviterService;

    @Autowired
    private LoginRegisterService loginRegisterService;

    @RequestMapping(value = "/common/preInviter", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult preInviter(String phoneNum, String invitedCode) {
        //首先判断用户是否是新用户
        boolean registered = loginRegisterService.isRegistered(phoneNum);
        if (registered) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, "您已是用户", null);
        }
        //判断是否是合法邀请码
        boolean validInvitedCode = loginRegisterService.isValidInvitedCode(invitedCode);
        if (!validInvitedCode) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_2, "非法邀请码", null);
        }
        //生成预邀请记录，内部已经判断是否已经被预邀请
        SuperResult result = preInviterService.genPreInviterRecord(phoneNum, invitedCode);
        return result;
    }

}
