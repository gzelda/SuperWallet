package com.superwallet.service;

import com.superwallet.common.CWalletInfo;
import com.superwallet.pojo.Lockwarehouse;
import com.superwallet.pojo.Transfer;
import com.superwallet.response.ResponseCWalletProfit;
import com.superwallet.response.ResponseCWalletProfitEntry;
import com.superwallet.response.ResponseCWalletSimProfit;

import java.util.List;

public interface CWalletService {
    List<CWalletInfo> listCWalletInfo(String UID);

    boolean transferMoney(String UID, Integer tokenType, Double tokenAmount, Double gasPrice);

    boolean withdrawRequest(String UID, Integer tokenType, Double tokenAmount);

    boolean withdraw(String UID, String WID, Integer tokenType, Double tokenAmount, Double gasPrice, String auditor, String remark, Integer status);

    List<Transfer> listHistoryBills(String UID, Integer tokenType);

    ResponseCWalletSimProfit listProfit(String UID);

    List<ResponseCWalletProfitEntry> getLockedOrderProfit(String UID, Integer tokenType);

    List<ResponseCWalletProfitEntry> getWithDrawProfit(String UID, Integer tokenType);

    List<ResponseCWalletProfitEntry> getAgentProfit(String UID, Integer tokenType);

    List<ResponseCWalletProfitEntry> getInvitingProfit(String UID, Integer tokenType);

    List<ResponseCWalletProfitEntry> getWithDrawRecord(String UID, Integer tokenType);

    List<ResponseCWalletProfitEntry> getWithDrawOnProcess(String UID, Integer tokenType);

    ResponseCWalletProfitEntry finishedLockedOrderToEntry(Lockwarehouse order);

    List<ResponseCWalletProfitEntry> totalProfitToEntry(String UID, Integer tokenType, String orderID, Integer profitType);

    ResponseCWalletProfit listDetailProfit(String UID, Integer tokenType, Integer type);

    boolean updateETHWalletAmount(String UID, double amount, Integer type);

    boolean updateEOSWalletAmount(String UID, double amount, Integer type);

    boolean updateBGSWalletAmount(String UID, double amount, Integer type);

}
