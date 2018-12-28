package com.superwallet.service;

import com.superwallet.common.CommonWalletInfo;
import com.superwallet.common.EOSWalletInfo;
import com.superwallet.common.ETHWalletInfo;
import com.superwallet.common.SuperResult;
import com.superwallet.pojo.Lockwarehouse;
import com.superwallet.response.ResponseCWalletSimProfitEntry;
import com.superwallet.response.ResponseDWalletLockedOrderEntry;

public interface CommonService {
    public void generateRecord(String UID, Byte transferType, Byte tokenType, Byte status, String addressFrom, String addressTo, Double tokenAmount);

    public void withdrawRecord(String UID, String WID, Byte tokenType, Byte status, Double tokenAmount);

    public void lockedRecord(String UID, Integer tokenType, Integer period, Double tokenAmount, Integer status);

    public SuperResult getETHInfo(String UID);

    public SuperResult getEOSInfo(String UID);

    public ETHWalletInfo getETHDetailInfo(String UID);

    public ETHWalletInfo getBGSDetailInfo(String UID);

    public EOSWalletInfo getEOSDetailInfo(String UID);

    public CommonWalletInfo getMappingCWalletInfo(String UID, Integer tokenType);

    public CommonWalletInfo getMappingDAndCWalletInfo(String UID, Integer tokenType);

    public ResponseDWalletLockedOrderEntry lockedOrderToEntry(Lockwarehouse row);

    public double getLockedAmount(String UID, Integer tokenType);

    public int getInvitingCount(String UID);

    public int getAgentInvitingCount(String UID);

    public int getAgentCount();

    public ResponseCWalletSimProfitEntry getCWalletTokenProfit(String UID, Integer tokenType);

    public void initToken(String UID, Integer tokenType);

    public SuperResult transferOnChain(String UID, Double tokenAmount, String fromAddress, String toAddress, Integer type, Integer chain);

    public Object getToken(String UID, Integer tokenType);

}
