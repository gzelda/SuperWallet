package com.superwallet.service;

import com.superwallet.common.CommonWalletInfo;
import com.superwallet.common.EOSWalletInfo;
import com.superwallet.common.ETHWalletInfo;
import com.superwallet.common.SuperResult;
import com.superwallet.pojo.Lockwarehouse;
import com.superwallet.response.ResponseCWalletSimProfitEntry;
import com.superwallet.response.ResponseDWalletLockedOrderEntry;

public interface CommonService {
    boolean generateRecord(String UID, Byte transferType, Byte tokenType, Byte status, String addressFrom, String addressTo, Double tokenAmount);

    boolean withdrawRecord(String UID, String WID, Byte tokenType, Byte status, Double tokenAmount);

    boolean lockedRecord(String UID, Integer tokenType, Integer period, Double tokenAmount, Integer status);

    SuperResult getETHInfo(String UID);

    SuperResult getEOSInfo(String UID);

    ETHWalletInfo getETHDetailInfo(String UID);

    ETHWalletInfo getBGSDetailInfo(String UID);

    EOSWalletInfo getEOSDetailInfo(String UID);

    CommonWalletInfo getMappingCWalletInfo(String UID, Integer tokenType);

    CommonWalletInfo getMappingDAndCWalletInfo(String UID, Integer tokenType);

    ResponseDWalletLockedOrderEntry lockedOrderToEntry(Lockwarehouse row);

    double getLockedAmount(String UID, Integer tokenType);

    int getInvitingCount(String UID);

    int getAgentInvitingCount(String UID);

    int getAgentCount();

    ResponseCWalletSimProfitEntry getCWalletTokenProfit(String UID, Integer tokenType);

    boolean initToken(String UID, String address, Integer tokenType);

    SuperResult ETHTransfer(String UID, Double tokenAmount, Double gasPrice, String fromAddress, String toAddress, Integer type);

    SuperResult EOSTransfer(String UID, Double tokenAmount, String fromAddress, String toAddress, Integer type, String memo);

    Object getToken(String UID, Integer tokenType);

    double getTokenPriceByType(Integer tokenType);

    String parseEOSJson(String value);

    boolean hasEOSWallet(String UID);

    SuperResult allocateEOSWallet(String UID);

    boolean updateUserEOSWallet(String UID);

    SuperResult createETHAddress(String UID);

    boolean hasMoreEOSWallet();

    double getMinTransferAmount(Integer tokenType);

    double getMinWithdrawAmount(Integer tokenType);

    double getMinLockAmount(Integer tokenType);

    SuperResult recycleWallet(String UID);
}
