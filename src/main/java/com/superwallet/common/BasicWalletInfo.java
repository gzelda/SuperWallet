package com.superwallet.common;

public class BasicWalletInfo {
    private double amount;
    private double lockedAmount;
    private double availableAmount;
    private double price;
    private byte canLock;

    public BasicWalletInfo(double amount, double lockedAmount, double availableAmount, double price, byte canLock) {
        this.amount = amount;
        this.lockedAmount = lockedAmount;
        this.availableAmount = availableAmount;
        this.price = price;
        this.canLock = canLock;
    }

    public byte getCanLock() {
        return canLock;
    }

    public void setCanLock(byte canLock) {
        this.canLock = canLock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

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


    public BasicWalletInfo() {
    }
}
