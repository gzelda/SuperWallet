package com.superwallet.controller;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.SuperResult;
import com.superwallet.common.WalletInfo;
import com.superwallet.pojo.Transfer;
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

    /**
     * 链上钱包转出，中心钱包转入
     *
     * @param UID
     * @param tokenType
     * @param tokenAmount
     * @return
     */
    @RequestMapping(value = "/cWallet/transferMoney", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult transferMoney(String UID, int tokenType, double tokenAmount) {
        boolean result = cWalletService.transferMoney(UID, tokenType, tokenAmount);
        if (result) {
            return SuperResult.ok();
        }
        return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
    }

    /**
     * 中心钱包提现
     *
     * @param UID
     * @param tokenType
     * @param tokenAmount
     * @return
     */
    @RequestMapping(value = "/cWallet/withdraw", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult withdraw(String UID, int tokenType, double tokenAmount, HttpServletRequest request) {
        boolean result = cWalletService.withdraw(UID, tokenType, tokenAmount);
        if (result) return SuperResult.ok();
        return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
    }

    /**
     * 交易记录查询
     *
     * @param UID
     * @param tokenType
     * @return
     */
    @RequestMapping(value = "/cWallet/listHistoryBills", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listHistoryBills(String UID, int tokenType) {
        //获取历史交易
        List<Transfer> list = cWalletService.listHistoryBills(UID, tokenType);
        return SuperResult.ok(list);
    }

    /**
     * 用户在中心钱包购买代理人
     *
     * @param UID
     * @return
     */
    @RequestMapping(value = "/cWallet/buyAgent", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult buyAgent(String UID) {
        boolean result = cWalletService.buyAgent(UID);
        if (!result) return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
        return SuperResult.ok();
    }


}
