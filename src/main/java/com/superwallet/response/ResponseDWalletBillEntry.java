package com.superwallet.response;

import java.io.Serializable;

public class ResponseDWalletBillEntry implements Serializable {
    private String transferType;
    private String transferState;
    private double transferAmount;
    private double transferAmountToRMB;
    private String transferTime;

    public ResponseDWalletBillEntry() {
    }

    public ResponseDWalletBillEntry(String transferType, String transferState, double transferAmount, double transferAmountToRMB, String transferTime) {
        this.transferType = transferType;
        this.transferState = transferState;
        this.transferAmount = transferAmount;
        this.transferAmountToRMB = transferAmountToRMB;
        this.transferTime = transferTime;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferState() {
        return transferState;
    }

    public void setTransferState(String transferState) {
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
