package com.superwallet.service;

import com.superwallet.common.SuperResult;
import com.superwallet.response.*;

import java.util.List;

public interface DWalletService {
    public SuperResult transferMoney(String UID, Integer tokenType, Double tokenAmount, Double tokenPrice, String addressTo, String memo);

    public SuperResult lock(String UID, Integer tokenType, Double tokenAmount, Double gasPrice, Integer period);

    public ResponseDWalletLockedOrder listOrders(String UID, Integer tokenType);

    public boolean buyOrSellEOSCPUNET(String UID, Double cpuAmount, Double netAmount, Integer actionType);

    public boolean buyOrSellRAM(String UID, Double ramAmount, Integer actionType);

    public ResponseDWalletEOSDetailInfo listEOSDetailInfo(String UID);

    public List<ResponseDWalletSimpleInfo> listDWalletInfo(String UID);

    public ResponseDWalletBill listDetailDWalletInfo(String UID, Integer tokenType, Integer type);

    public ResponseDWalletAssets listAssets(String UID);

    public ResponseDWalletLockedOrderEntry getOrder(String UID, String LID);

    public SuperResult getOrRequestIdentity(String UID);

    public SuperResult identityFromPermissions(String UID);

    public SuperResult requestSignature(String UID);

    public SuperResult getOriginData(String UID);
}
