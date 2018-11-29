package com.superwallet.controller;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.SuperResult;
import com.superwallet.common.WalletInfo;
import com.superwallet.service.CWalletService;
import com.superwallet.service.LoginRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class CWalletController {

    @Autowired
    private CWalletService cWalletService;

    @Autowired
    private LoginRegisterService loginRegisterService;

    /**
     * 请求中心钱包信息列表
     *
     * @param UID
     * @param request
     * @return
     */
    @RequestMapping(value = "/cWallet/listCWalletInfo", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listCWalletInfo(String UID, HttpServletRequest request) {
        //TODO 判断是否登录超时
        List<WalletInfo> walletInfos = cWalletService.listCWalletInfo(UID);
        //如果钱包返回总量信息不对，将返回系统异常
        if (walletInfos.size() != CodeRepresentation.COUNT_WALLETS)
            return new SuperResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, null);
        HashMap<String, WalletInfo> map = new HashMap<String, WalletInfo>();
        map.put(CodeRepresentation.ETHINFO, walletInfos.get(0));
        map.put(CodeRepresentation.BGSINFO, walletInfos.get(1));
        map.put(CodeRepresentation.EOSINFO, walletInfos.get(2));
        return SuperResult.ok(map);
    }
}
