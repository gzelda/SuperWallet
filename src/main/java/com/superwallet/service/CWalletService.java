package com.superwallet.service;

import com.superwallet.common.CWalletInfo;
import com.superwallet.pojo.Transfer;

import java.util.List;

public interface CWalletService {
    public List<CWalletInfo> listCWalletInfo(String UID);

    public boolean transferMoney(String UID, Integer tokenType, Double tokenAmount);

    public boolean withdraw(String UID, String WID, Integer tokenType, Double tokenAmount);

    public boolean withdrawRequest(String UID, Integer tokenType, Double tokenAmount);

    public List<Transfer> listHistoryBills(String UID, Integer tokenType);

    public boolean buyAgent(String UID);
}
