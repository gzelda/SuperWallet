package com.superwallet.service;

import com.superwallet.common.SuperResult;
import com.superwallet.response.*;

import java.util.List;

public interface DWalletService {
    public SuperResult transferMoney(String UID, Integer tokenType, Double tokenAmount, Double tokenPrice, String addressTo, String memo);

    public SuperResult lock(String UID, Integer tokenType, Double tokenAmount, Double gasPrice, Integer period);

    public ResponseDWalletLockedOrder listOrders(String UID, Integer tokenType);

    public SuperResult trxEOSCPU(String UID);

    public SuperResult trxEOSNET(String UID);

    public boolean buyOrSellEOSCPUNET(String UID, Double cpuAmount, Double netAmount, Integer actionType);

    public boolean buyOrSellRAM(String UID, Double ramAmount, Integer actionType);

    public ResponseDWalletEOSDetailInfo listEOSDetailInfo(String UID);

    public List<ResponseDWalletSimpleInfo> listDWalletInfo(String UID);

    public ResponseDWalletBill listDetailDWalletInfo(String UID, Integer tokenType, Integer type);

    public List<ResponseDWalletBillEntry> listDetailDWalletLockOrdersInfo(String UID, Integer tokenType);

    public List<ResponseDWalletBillEntry> listDetailDWalletTransferInfo(String UID, Integer tokenType, Integer type);

    public ResponseDWalletAssets listAssets(String UID);

    public ResponseDWalletLockedOrderEntry getOrder(String UID, String LID);

    public SuperResult buyAgent(String UID);
}
