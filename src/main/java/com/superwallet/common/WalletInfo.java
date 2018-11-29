package com.superwallet.common;

import java.io.Serializable;

public class WalletInfo implements Serializable {
    private String address;
    private double amount;
    private double HUOBIprice;
    private double lockedAmount;
    private double availableAmount;

    public WalletInfo() {
    }


    public WalletInfo(String address, double amount, double HUOBIprice, double lockedAmount, double availableAmount) {
        this.address = address;
        this.amount = amount;
        this.HUOBIprice = HUOBIprice;
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

    public double getHUOBIprice() {
        return HUOBIprice;
    }

    public void setHUOBIprice(double HUOBIprice) {
        this.HUOBIprice = HUOBIprice;
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
