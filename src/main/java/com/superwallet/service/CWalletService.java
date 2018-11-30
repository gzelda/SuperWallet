package com.superwallet.service;

import com.superwallet.common.WalletInfo;
import com.superwallet.pojo.Transfer;

import java.util.List;

public interface CWalletService {
    public List<WalletInfo> listCWalletInfo(String UID);

    public boolean transferMoney(String UID, Integer tokenType, Integer tokenAmount);

    public boolean withdraw(String UID, Integer tokenType, Integer tokenAmount);

    public List<Transfer> listHistoryBills(String UID, Integer tokenType);
}
