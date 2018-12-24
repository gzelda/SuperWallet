package com.superwallet.service;

import com.alibaba.fastjson.JSONObject;
import com.superwallet.common.EOSWalletInfo;
import com.superwallet.common.ETHWalletInfo;

public interface CommonService {
    public void generateRecord(String UID, Byte transferType, Byte tokenType, Byte status, String addressFrom, String addressTo, Double tokenAmount);

    public void withdrawRecord(String UID, String WID, Byte tokenType, Byte status, Double tokenAmount);

    public void lockedRecord(String UID, Integer period, Double tokenAmount, Byte status);

    public JSONObject getETHInfo(String UID);

    public JSONObject getEOSInfo(String UID);

    public ETHWalletInfo getETHDetailInfo(String UID);

    public ETHWalletInfo getBGSDetailInfo(String UID);

    public EOSWalletInfo getEOSDetailInfo(String UID);
}
