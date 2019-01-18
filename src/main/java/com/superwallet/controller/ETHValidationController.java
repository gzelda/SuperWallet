package com.superwallet.controller;

import com.superwallet.common.SuperResult;
import com.superwallet.service.ETHValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ETH链上订单交易状态确认Controller
 */
@Controller
public class ETHValidationController {

    @Autowired
    private ETHValidationService ethValidationService;

    @RequestMapping(value = "/queryPending")
    @ResponseBody
    public SuperResult queryPending() {
        ethValidationService.ETHValidation();
        return SuperResult.ok();
    }
}
