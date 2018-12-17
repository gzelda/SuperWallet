package com.superwallet.service;

public interface CommonService {
    public void generateRecord(String UID, Byte transferType, Byte tokenType, Byte status, String addressFrom, String addressTo, Double tokenAmount);

    public void withdrawRecord(String UID, String WID, Byte tokenType, Byte status, Double tokenAmount);

    public void lockedRecord(String UID, Integer period, Double tokenAmount, Byte status);
}
