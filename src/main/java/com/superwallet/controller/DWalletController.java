package com.superwallet.controller;

import com.superwallet.common.BasicWalletInfo;
import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.SuperResult;
import com.superwallet.pojo.Lockwarehouse;
import com.superwallet.service.DWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 链上钱包模块
 */
@Controller
public class DWalletController {

    @Autowired
    private DWalletService dWalletService;

    @RequestMapping(value = "/dWallet/listWalletInfo", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listWalletInfo(String UID) {
        List<BasicWalletInfo> walletInfos = dWalletService.listWalletInfo(UID);
        return SuperResult.ok(walletInfos);
    }

    @RequestMapping(value = "/dWallet/transferMoney", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult transferMoney(String UID, Integer tokenType, double tokenAmount, String addressTo, String description) {
        boolean res = dWalletService.transferMoney(UID, tokenType, tokenAmount, addressTo, description);
        if (!res) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
        }
        return SuperResult.ok();
    }

    @RequestMapping(value = "/dWallet/lock", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult lock(String UID, Integer tokenType, Double tokenAmount, int period) {
        boolean res = dWalletService.lock(UID, tokenType, tokenAmount, period);
        if (!res) return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
        return SuperResult.ok();
    }

    @RequestMapping(value = "/dWallet/listOrders", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listOrders(String UID, String timeStampLeft, String timeStampRight) {
        List<Lockwarehouse> res = dWalletService.listOrders(UID, timeStampLeft, timeStampRight);
        return SuperResult.ok(res);
    }

}
