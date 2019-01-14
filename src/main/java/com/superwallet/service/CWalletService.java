package com.superwallet.service;

import com.superwallet.common.CWalletInfo;
import com.superwallet.pojo.Lockwarehouse;
import com.superwallet.pojo.Transfer;
import com.superwallet.response.ResponseCWalletProfit;
import com.superwallet.response.ResponseCWalletProfitEntry;
import com.superwallet.response.ResponseCWalletSimProfit;

import java.util.List;

public interface CWalletService {
    public List<CWalletInfo> listCWalletInfo(String UID);

    public boolean transferMoney(String UID, Integer tokenType, Double tokenAmount, Double gasPrice);

    public boolean withdrawRequest(String UID, Integer tokenType, Double tokenAmount);

    public boolean withdraw(String UID, String WID, Integer tokenType, Double tokenAmount, Double gasPrice);

    public List<Transfer> listHistoryBills(String UID, Integer tokenType);

    public ResponseCWalletSimProfit listProfit(String UID);

    public List<ResponseCWalletProfitEntry> getLockedOrderProfit(String UID, Integer tokenType);

    public List<ResponseCWalletProfitEntry> getWithDrawProfit(String UID, Integer tokenType);

    public List<ResponseCWalletProfitEntry> getAgentProfit(String UID, Integer tokenType);

    public List<ResponseCWalletProfitEntry> getInvitingProfit(String UID, Integer tokenType);

    public ResponseCWalletProfitEntry finishedLockedOrderToEntry(Lockwarehouse order);

    public List<ResponseCWalletProfitEntry> totalProfitToEntry(String UID, Integer tokenType, String orderID, Integer profitType);

    public ResponseCWalletProfit listDetailProfit(String UID, Integer tokenType, Integer type);

    public boolean updateETHWalletAmount(String UID, double amount, Integer type);

    public boolean updateEOSWalletAmount(String UID, double amount, Integer type);

    public boolean updateBGSWalletAmount(String UID, double amount, Integer type);

}
