package com.superwallet.controller;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.SuperResult;
import com.superwallet.response.ResponseDWalletAssets;
import com.superwallet.response.ResponseDWalletLockedOrder;
import com.superwallet.response.ResponseDWalletLockedOrderEntry;
import com.superwallet.response.ResponseDWalletSimpleInfo;
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

    /**
     * 查询链上钱包信息
     *
     * @param UID
     * @return
     */
    @RequestMapping(value = "/dWallet/listWalletInfo", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listWalletInfo(String UID) {
        List<ResponseDWalletSimpleInfo> walletInfos = dWalletService.listWalletInfo(UID);
        return SuperResult.ok(walletInfos);
    }

    /**
     * 链上钱包转账
     *
     * @param UID
     * @param tokenType
     * @param tokenAmount
     * @param addressTo
     * @param description
     * @return
     */
    @RequestMapping(value = "/dWallet/transferMoney", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult transferMoney(String UID, Integer tokenType, double tokenAmount, String addressTo, String description) {
        boolean res = dWalletService.transferMoney(UID, tokenType, tokenAmount, addressTo, description);
        if (!res) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
        }
        return SuperResult.ok();
    }

    /**
     * 锁仓
     *
     * @param UID
     * @param tokenType
     * @param tokenAmount
     * @param period
     * @return
     */
    @RequestMapping(value = "/dWallet/lock", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult lock(String UID, Integer tokenType, Double tokenAmount, int period) {
        boolean res = dWalletService.lock(UID, tokenType, tokenAmount, period);
        if (!res) return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
        return SuperResult.ok();
    }

    /**
     * 锁仓订单查询
     *
     * @param UID
     * @param tokenType
     * @return
     */
    @RequestMapping(value = "/dWallet/listOrders", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listOrders(String UID, int tokenType) {
        ResponseDWalletLockedOrder res = dWalletService.listOrders(UID, tokenType);
        return SuperResult.ok(res);
    }

    /**
     * 某个锁仓订单详情查询
     *
     * @param UID
     * @param LID
     * @return
     */
    @RequestMapping(value = "/dWallet/listOrders", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listOrders(String UID, String LID) {
        ResponseDWalletLockedOrderEntry res = dWalletService.getOrder(UID, LID);
        return SuperResult.ok(res);
    }

    /**
     * 链上钱包详细信息查询--包括交易记录
     *
     * @param UID
     * @return
     */
    @RequestMapping(value = "/dWallet/listDWalletInfo", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult listBills(String UID) {
        ResponseDWalletAssets res = dWalletService.listDWalletInfo(UID);
        return SuperResult.ok(res);
    }

    /**
     * 质押、赎回EOS的CPUNET资源
     *
     * @param UID
     * @param cpuAmount
     * @param netAmount
     * @param actionType
     * @return
     */
    @RequestMapping(value = "/dWallet/trxCPUNET", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult trxCPUNET(String UID, Double cpuAmount, Double netAmount, Integer actionType) {
        boolean res = dWalletService.buyOrSellEOSCPUNET(UID, cpuAmount, netAmount, actionType);
        if (!res) return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
        return SuperResult.ok();
    }

    /**
     * RAM资源的controller
     *
     * @param UID
     * @param ramAmount
     * @param actionType
     * @return
     */
    @RequestMapping(value = "/dWallet/trxRAM", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult trxRAM(String UID, Double ramAmount, Integer actionType) {
        boolean res = dWalletService.buyOrSellRAM(UID, ramAmount, actionType);
        if (!res) return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
        return SuperResult.ok();
    }
}
