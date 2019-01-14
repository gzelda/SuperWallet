package com.superwallet.response;

import java.io.Serializable;

public class ResponseDWalletAssetsEntry implements Serializable {
    private int tokenType;
    private double tokenAmount;
    private double tokenAmountToRMB;
    private String tokenName;
    private int canLock;
    private double lockedAmount;
    private double tokenLockedProfit;

    public ResponseDWalletAssetsEntry() {
    }

    public ResponseDWalletAssetsEntry(double tokenAmount, double tokenAmountToRMB, String tokenName, int canLock, double lockedAmount, double tokenLockedProfit) {
        this.tokenAmount = tokenAmount;
        this.tokenAmountToRMB = tokenAmountToRMB;
        this.tokenName = tokenName;
        this.canLock = canLock;
        this.lockedAmount = lockedAmount;
        this.tokenLockedProfit = tokenLockedProfit;
    }

    public ResponseDWalletAssetsEntry(int tokenType, double tokenAmount, double tokenAmountToRMB, String tokenName, int canLock, double lockedAmount, double tokenLockedProfit) {
        this.tokenType = tokenType;
        this.tokenAmount = tokenAmount;
        this.tokenAmountToRMB = tokenAmountToRMB;
        this.tokenName = tokenName;
        this.canLock = canLock;
        this.lockedAmount = lockedAmount;
        this.tokenLockedProfit = tokenLockedProfit;
    }

    public int getTokenType() {
        return tokenType;
    }

    public void setTokenType(int tokenType) {
        this.tokenType = tokenType;
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

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public int getCanLock() {
        return canLock;
    }

    public void setCanLock(int canLock) {
        this.canLock = canLock;
    }

    public double getLockedAmount() {
        return lockedAmount;
    }

    public void setLockedAmount(double lockedAmount) {
        this.lockedAmount = lockedAmount;
    }

    public double getTokenLockedProfit() {
        return tokenLockedProfit;
    }

    public void setTokenLockedProfit(double tokenLockedProfit) {
        this.tokenLockedProfit = tokenLockedProfit;
    }
}
