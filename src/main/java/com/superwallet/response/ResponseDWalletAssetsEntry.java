package com.superwallet.response;

import java.io.Serializable;

public class ResponseDWalletAssetsEntry implements Serializable {
    private double coinTotal;
    private double coinRmbTotal;
    private String coinName;
    private byte coinCanLock;
    private double coinLockCount;
    private double coinLockProfit;

    public ResponseDWalletAssetsEntry() {
    }

    public ResponseDWalletAssetsEntry(double coinTotal, double coinRmbTotal, String coinName, byte coinCanLock, double coinLockCount, double coinLockProfit) {
        this.coinTotal = coinTotal;
        this.coinRmbTotal = coinRmbTotal;
        this.coinName = coinName;
        this.coinCanLock = coinCanLock;
        this.coinLockCount = coinLockCount;
        this.coinLockProfit = coinLockProfit;
    }

    public double getCoinTotal() {
        return coinTotal;
    }

    public void setCoinTotal(double coinTotal) {
        this.coinTotal = coinTotal;
    }

    public double getCoinRmbTotal() {
        return coinRmbTotal;
    }

    public void setCoinRmbTotal(double coinRmbTotal) {
        this.coinRmbTotal = coinRmbTotal;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public byte getCoinCanLock() {
        return coinCanLock;
    }

    public void setCoinCanLock(byte coinCanLock) {
        this.coinCanLock = coinCanLock;
    }

    public double getCoinLockCount() {
        return coinLockCount;
    }

    public void setCoinLockCount(double coinLockCount) {
        this.coinLockCount = coinLockCount;
    }

    public double getCoinLockProfit() {
        return coinLockProfit;
    }

    public void setCoinLockProfit(double coinLockProfit) {
        this.coinLockProfit = coinLockProfit;
    }
}
