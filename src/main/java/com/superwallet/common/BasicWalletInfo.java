package com.superwallet.common;

public class BasicWalletInfo {
    private double amount;
    private double lockedAmount;
    private double availableAmount;
    private double HUOBIprice;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public double getHUOBIprice() {
        return HUOBIprice;
    }

    public void setHUOBIprice(double HUOBIprice) {
        this.HUOBIprice = HUOBIprice;
    }

    public BasicWalletInfo() {
    }

    public BasicWalletInfo(double amount, double lockedAmount, double availableAmount, double HUOBIprice) {
        this.amount = amount;
        this.lockedAmount = lockedAmount;
        this.availableAmount = availableAmount;
        this.HUOBIprice = HUOBIprice;
    }
}
