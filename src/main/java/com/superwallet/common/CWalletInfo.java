package com.superwallet.common;

import java.io.Serializable;

public class CWalletInfo implements Serializable {
    private String address;
    private double amount;
    private double tokenPrice;
    private double lockedAmount;
    private double availableAmount;

    public CWalletInfo() {
    }

    public CWalletInfo(String address, double amount, double tokenPrice, double lockedAmount, double availableAmount) {
        this.address = address;
        this.amount = amount;
        this.tokenPrice = tokenPrice;
        this.lockedAmount = lockedAmount;
        this.availableAmount = availableAmount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTokenPrice() {
        return tokenPrice;
    }

    public void setTokenPrice(double tokenPrice) {
        this.tokenPrice = tokenPrice;
    }

    public double getLockedAmount() {
        return lockedAmount;
    }

    public void setLockedAmount(double lockedAmount) {
        this.lockedAmount = lockedAmount;
    }

    public double getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(double availableAmount) {
        this.availableAmount = availableAmount;
    }

}
