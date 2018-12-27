package com.superwallet.controller;

import com.superwallet.common.CWalletInfo;
import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.MessageRepresentation;
import com.superwallet.common.SuperResult;
import com.superwallet.pojo.Transfer;
import com.superwallet.response.ResponseCWalletProfit;
import com.superwallet.response.ResponseCWalletSimProfit;
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
        List<CWalletInfo> walletInfos = cWalletService.listCWalletInfo(UID);
        //如果钱包返回总量信息不对，将返回系统异常
        if (walletInfos.size() != CodeRepresentation.COUNT_WALLETS)
            return new SuperResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, MessageRepresentation.ERROR_MSG,
                    null);
        HashMap<String, CWalletInfo> map = new HashMap<String, CWalletInfo>();
        map.put(CodeRepresentation.ETHINFO, walletInfos.get(0));
        map.put(CodeRepresentation.BGSINFO, walletInfos.get(1));
        map.put(CodeRepresentation.EOSINFO, walletInfos.get(2));
        SuperResult result = SuperResult.ok(map);
        result.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return result;
    }

    /**
     * 中心钱包转入
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
            SuperResult out = SuperResult.ok();
            out.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
            return out;
        }
        return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
    }

    /**
     * 中心钱包提现请求
     *
     * @param UID
     * @param tokenType
     * @param tokenAmount
     * @return
     */
    @RequestMapping(value = "/cWallet/withdrawRequest", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult withdrawRequest(String UID, int tokenType, double tokenAmount) {
        boolean result = cWalletService.withdrawRequest(UID, tokenType, tokenAmount);
        if (result) return SuperResult.ok(MessageRepresentation.CWALLET_WITHDRAWREQUEST_CODE_1_STATUS_0);
        return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.CWALLET_WITHDRAWREQUEST_CODE_0_STATUS_0, null);
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
    public SuperResult withdraw(String UID, String WID, int tokenType, double tokenAmount) {
        boolean result = cWalletService.withdraw(UID, WID, tokenType, tokenAmount);
        if (result) return SuperResult.ok(MessageRepresentation.CWALLET_WITHDRAW_CODE_1_STATUS_0);
        return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.CWALLET_WITHDRAW_CODE_0_STATUS_0, null);
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
        SuperResult result = SuperResult.ok(list);
        result.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return result;
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
        if (!result)
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.CWALLET_BUYAGENT_CODE_0_STATUS_0, null);
        return SuperResult.ok(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
    }

    /**
     * 中心钱包收益列表展示
     *
     * @param UID
     * @return
     */
    @RequestMapping(value = "/cWallet/listProfit", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listProfit(String UID) {
        ResponseCWalletSimProfit result = cWalletService.listProfit(UID);
        SuperResult out = SuperResult.ok(result);
        out.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return out;
    }

    /**
     * 特定币种收益详单查询
     *
     * @param UID
     * @param tokenType
     * @return
     */
    @RequestMapping(value = "/cWallet/listDetailProfit", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listProfit(String UID, int tokenType) {
        ResponseCWalletProfit result = cWalletService.listDetailProfit(UID, tokenType);
        SuperResult out = SuperResult.ok(result);
        out.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return out;
    }


}
