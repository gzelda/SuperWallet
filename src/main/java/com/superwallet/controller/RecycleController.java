package com.superwallet.controller;

import com.superwallet.common.SuperResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 钱包回收Controller
 */
@Controller
public class RecycleController {

    @RequestMapping(value = "/recycle/recycleEOSWallet", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult recycleEOSWallet() {
        return null;
    }
}
