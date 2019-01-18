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
import com.superwallet.service.TokenService;
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

    @Autowired
    private TokenService tokenService;

    /**
     * 请求中心钱包信息列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cWallet/listCWalletInfo", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listCWalletInfo(HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT, null);
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
     * @param tokenType
     * @param tokenAmount
     * @return
     */
    @RequestMapping(value = "/cWallet/transferMoney", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult transferMoney(int tokenType, double tokenAmount, double gasPrice, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT, null);
        boolean result = cWalletService.transferMoney(UID, tokenType, tokenAmount, gasPrice);
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
     * @param tokenType
     * @param tokenAmount
     * @return
     */
    @RequestMapping(value = "/cWallet/withdrawRequest", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult withdrawRequest(int tokenType, double tokenAmount, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT, null);
        boolean result = cWalletService.withdrawRequest(UID, tokenType, tokenAmount);
        if (result) return SuperResult.ok(MessageRepresentation.CWALLET_WITHDRAWREQUEST_CODE_1_STATUS_0);
        return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.CWALLET_WITHDRAWREQUEST_CODE_0_STATUS_0, null);
    }

    /**
     * 中心钱包提现
     *
     * @param tokenType
     * @param tokenAmount
     * @return
     */
    @RequestMapping(value = "/cWallet/withdraw", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult withdraw(String UID, String WID, int tokenType, double tokenAmount, double gasPrice, HttpServletRequest request) {
//        String UID = tokenService.getUID(request);
        //登录超时
//        if (UID == null)
//            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT, null);
        boolean result = cWalletService.withdraw(UID, WID, tokenType, tokenAmount, gasPrice);
        if (result) return SuperResult.ok(MessageRepresentation.CWALLET_WITHDRAW_CODE_1_STATUS_0);
        return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.CWALLET_WITHDRAW_CODE_0_STATUS_0, null);
    }

    /**
     * 交易记录查询
     *
     * @param tokenType
     * @return
     */
    @RequestMapping(value = "/cWallet/listHistoryBills", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listHistoryBills(int tokenType, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT, null);
        //获取历史交易
        List<Transfer> list = cWalletService.listHistoryBills(UID, tokenType);
        SuperResult result = SuperResult.ok(list);
        result.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return result;
    }

    /**
     * 中心钱包收益列表展示
     *
     * @return
     */
    @RequestMapping(value = "/cWallet/listProfit", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listProfit(HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT, null);
        ResponseCWalletSimProfit result = cWalletService.listProfit(UID);
        SuperResult out = SuperResult.ok(result);
        out.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return out;
    }

    /**
     * 特定币种收益详单查询
     *
     * @param tokenType
     * @return
     */
    @RequestMapping(value = "/cWallet/listDetailProfit", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listProfit(int tokenType, int type, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT, null);
        ResponseCWalletProfit result = cWalletService.listDetailProfit(UID, tokenType, type);
        SuperResult out = SuperResult.ok(result);
        out.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return out;
    }


}
