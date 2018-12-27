package com.superwallet.service;

import com.superwallet.common.BasicWalletInfo;
import com.superwallet.common.EOSWalletInfo;
import com.superwallet.response.*;

import java.util.List;

public interface DWalletService {
    public List<BasicWalletInfo> listDetailWalletInfo(String UID);

    public boolean transferMoney(String UID, Integer tokenType, Double tokenAmount, String addressTo, String description);

    public boolean lock(String UID, Integer tokenType, Double tokenAmount, Integer period);

    public ResponseDWalletLockedOrder listOrders(String UID, Integer tokenType);

    public boolean buyOrSellEOSCPUNET(String UID, Double cpuAmount, Double netAmount, Integer actionType);

    public boolean buyOrSellRAM(String UID, Double ramAmount, Integer actionType);

    public EOSWalletInfo listEOSBasic(String UID);

    public List<ResponseDWalletSimpleInfo> listDWalletInfo(String UID);

    public ResponseDWalletBill listDetailDWalletInfo(String UID, Integer tokenType, Integer type);

    public ResponseDWalletAssets listAssets(String UID);

    public ResponseDWalletLockedOrderEntry getOrder(String UID, String LID);
}
