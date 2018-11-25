package com.superwallet.model;

public class userPrivateWithBLOBs extends userPrivate {
    private byte[] idCardFront;

    private byte[] idCardBack;

    public byte[] getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(byte[] idCardFront) {
        this.idCardFront = idCardFront;
    }

    public byte[] getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(byte[] idCardBack) {
        this.idCardBack = idCardBack;
    }
}