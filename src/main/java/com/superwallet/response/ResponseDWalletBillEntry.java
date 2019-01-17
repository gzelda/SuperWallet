package com.superwallet.response;

import java.io.Serializable;

public class ResponseDWalletBillEntry implements Serializable {
    private String transferType;
    private int transferState;
    private double transferAmount;
    private double transferAmountToRMB;
    private String transferTime;
    private int isIncoming;

    public ResponseDWalletBillEntry() {
    }

    public ResponseDWalletBillEntry(String transferType, int transferState, double transferAmount, double transferAmountToRMB, String transferTime, int isIncoming) {
        this.transferType = transferType;
        this.transferState = transferState;
        this.transferAmount = transferAmount;
        this.transferAmountToRMB = transferAmountToRMB;
        this.transferTime = transferTime;
        this.isIncoming = isIncoming;
    }

    public int getIsIncoming() {
        return isIncoming;
    }

    public void setIsIncoming(int isIncoming) {
        this.isIncoming = isIncoming;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public int getTransferState() {
        return transferState;
    }

    public void setTransferState(int transferState) {
        this.transferState = transferState;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public double getTransferAmountToRMB() {
        return transferAmountToRMB;
    }

    public void setTransferAmountToRMB(double transferAmountToRMB) {
        this.transferAmountToRMB = transferAmountToRMB;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }
}
