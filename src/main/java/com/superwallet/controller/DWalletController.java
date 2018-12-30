package com.superwallet.controller;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.MessageRepresentation;
import com.superwallet.common.SuperResult;
import com.superwallet.response.*;
import com.superwallet.service.DWalletService;
import com.superwallet.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 链上钱包模块
 */
@Controller
public class DWalletController {

    @Autowired
    private DWalletService dWalletService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询链上钱包信息
     *
     * @return
     */
    @RequestMapping(value = "/dWallet/listDWalletInfo", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listWalletInfo(HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        List<ResponseDWalletSimpleInfo> walletInfos = dWalletService.listDWalletInfo(UID);
        SuperResult result = SuperResult.ok(walletInfos);
        result.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return result;
    }

    /**
     * 链上钱包详细信息查询--包括交易记录
     *
     * @return
     */
    @RequestMapping(value = "/dWallet/listDetailDWalletInfo", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listBills(Integer tokenType, Integer type, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        ResponseDWalletBill res = dWalletService.listDetailDWalletInfo(UID, tokenType, type);
        SuperResult result = SuperResult.ok(res);
        result.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return result;
    }

    /**
     * 链上钱包资产模块展示
     *
     * @return
     */
    @RequestMapping(value = "/dWallet/listAssets", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listAssets(HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        ResponseDWalletAssets res = dWalletService.listAssets(UID);
        SuperResult result = SuperResult.ok(res);
        result.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return result;
    }

    /**
     * 锁仓订单查询
     *
     * @param tokenType
     * @return
     */
    @RequestMapping(value = "/dWallet/listOrders", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listOrders(int tokenType, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        ResponseDWalletLockedOrder res = dWalletService.listOrders(UID, tokenType);
        SuperResult result = SuperResult.ok(res);
        result.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return result;
    }

    /**
     * 某个锁仓订单详情查询
     *
     * @param LID
     * @return
     */
    @RequestMapping(value = "/dWallet/getOrder", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listOrders(String LID, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        ResponseDWalletLockedOrderEntry res = dWalletService.getOrder(UID, LID);
        SuperResult result = SuperResult.ok(res);
        result.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return result;
    }

    /**
     * 链上钱包转账
     *
     * @param tokenType
     * @param tokenAmount
     * @param addressTo
     * @param description
     * @return
     */
    @RequestMapping(value = "/dWallet/transferMoney", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult transferMoney(Integer tokenType, double tokenAmount, double gasPrice, String addressTo, String description, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        SuperResult res = dWalletService.transferMoney(UID, tokenType, tokenAmount, gasPrice, addressTo, description);
        return res;
    }

    /**
     * 锁仓
     *
     * @param tokenType
     * @param tokenAmount
     * @param period
     * @return
     */
    @RequestMapping(value = "/dWallet/lock", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult lock(Integer tokenType, Double tokenAmount, Double gasPrice, int period, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        SuperResult res = dWalletService.lock(UID, tokenType, tokenAmount, gasPrice, period);
        return res;
    }

    /**
     * 展示用户EOS账户的详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/dWallet/listEOSDetailInfo")
    @ResponseBody
    public SuperResult listEOSDetailInfo(HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        ResponseDWalletEOSDetailInfo result = dWalletService.listEOSDetailInfo(UID);
        return SuperResult.ok(result);
    }

    /**
     * 质押、赎回EOS的CPUNET资源
     *
     * @param cpuAmount
     * @param netAmount
     * @param actionType
     * @return
     */
    @RequestMapping(value = "/dWallet/trxCPUNET", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult trxCPUNET(Double cpuAmount, Double netAmount, Integer actionType, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        boolean res = dWalletService.buyOrSellEOSCPUNET(UID, cpuAmount, netAmount, actionType);
        if (!res) return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
        SuperResult result = SuperResult.ok();
        result.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return result;
    }

    /**
     * RAM资源的controller
     *
     * @param ramAmount
     * @param actionType
     * @return
     */
    @RequestMapping(value = "/dWallet/trxRAM", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult trxRAM(Double ramAmount, Integer actionType, HttpServletRequest request) {
        String UID = tokenService.getUID(request);
        //登录超时
        if (UID == null)
            return new SuperResult(CodeRepresentation.CODE_TIMEOUT, CodeRepresentation.STATUS_TIMEOUT, MessageRepresentation.USER_USER_CODE_TIMEOUT_STATUS_TIMEOUT);
        boolean res = dWalletService.buyOrSellRAM(UID, ramAmount, actionType);
        if (!res) return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
        SuperResult result = SuperResult.ok();
        result.setMsg(MessageRepresentation.SUCCESS_CODE_1_STATUS_0);
        return result;
    }
}
