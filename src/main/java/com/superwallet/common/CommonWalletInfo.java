package com.superwallet.common;

import java.io.Serializable;

public class CommonWalletInfo implements Serializable {
    private int tokenType;//货币类型
    private String tokenName;//货币名称
    private String tokenAddress;//货币地址
    private double lockedAmount;//锁仓金额
    private double cWalletAmount;//中心钱包余额
    private double balance;//链上余额
    private int canLock;//货币是否可以锁仓
    private double tokenPrice;//货币价值

    public CommonWalletInfo(int tokenType, String tokenName, String tokenAddress, double lockedAmount, double cWalletAmount, double balance, int canLock, double tokenPrice) {
        this.tokenType = tokenType;
        this.tokenName = tokenName;
        this.tokenAddress = tokenAddress;
        this.lockedAmount = lockedAmount;
        this.cWalletAmount = cWalletAmount;
        this.balance = balance;
        this.canLock = canLock;
        this.tokenPrice = tokenPrice;
    }

    public CommonWalletInfo(int tokenType, String tokenName, String tokenAddress, double lockedAmount, double cWalletAmount, int canLock) {
        this.tokenType = tokenType;
        this.tokenName = tokenName;
        this.tokenAddress = tokenAddress;
        this.lockedAmount = lockedAmount;
        this.cWalletAmount = cWalletAmount;
        this.canLock = canLock;
    }

    public CommonWalletInfo(int tokenType, String tokenName, String tokenAddress, double lockedAmount, double cWalletAmount, double balance, int canLock) {
        this.tokenType = tokenType;
        this.tokenName = tokenName;
        this.tokenAddress = tokenAddress;
        this.lockedAmount = lockedAmount;
        this.cWalletAmount = cWalletAmount;
        this.balance = balance;
        this.canLock = canLock;
    }

    public CommonWalletInfo() {
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

    public double getLockedAmount() {
        return lockedAmount;
    }

    public void setLockedAmount(double lockedAmount) {
        this.lockedAmount = lockedAmount;
    }

    public double getcWalletAmount() {
        return cWalletAmount;
    }

    public void setcWalletAmount(double cWalletAmount) {
        this.cWalletAmount = cWalletAmount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCanLock() {
        return canLock;
    }

    public void setCanLock(int canLock) {
        this.canLock = canLock;
    }

    public double getTokenPrice() {
        return tokenPrice;
    }

    public void setTokenPrice(double tokenPrice) {
        this.tokenPrice = tokenPrice;
    }
}
