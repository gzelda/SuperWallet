package com.superwallet.response;

import java.io.Serializable;

public class ResponseDWalletBillWalletEntry implements Serializable {
    private int tokenType;
    private String tokenName;
    private String tokenAddress;
    private double tokenAmount;
    private double tokenAmountToRMB;
    private double lockedAmount;

    public ResponseDWalletBillWalletEntry(int tokenType, String tokenName, String tokenAddress, double tokenAmount, double tokenAmountToRMB, double lockedAmount) {
        this.tokenType = tokenType;
        this.tokenName = tokenName;
        this.tokenAddress = tokenAddress;
        this.tokenAmount = tokenAmount;
        this.tokenAmountToRMB = tokenAmountToRMB;
        this.lockedAmount = lockedAmount;
    }

    public int getTokenType() {
        return tokenType;
    }

    public void setTokenType(int tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public double getTokenAmount() {
        return tokenAmount;
    }

    public void setTokenAmount(double tokenAmount) {
        this.tokenAmount = tokenAmount;
    }

    public double getTokenAmountToRMB() {
        return tokenAmountToRMB;
    }

    public void setTokenAmountToRMB(double tokenAmountToRMB) {
        this.tokenAmountToRMB = tokenAmountToRMB;
    }

    public double getLockedAmount() {
        return lockedAmount;
    }

    public void setLockedAmount(double lockedAmount) {
        this.lockedAmount = lockedAmount;
    }
}
