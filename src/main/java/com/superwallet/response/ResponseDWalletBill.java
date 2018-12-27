package com.superwallet.response;

import java.io.Serializable;
import java.util.List;

public class ResponseDWalletBill implements Serializable {

    private String tokenName;
    private double tokenAmount;
    private double tokenAmountToRmb;
    private double lockedAmount;
    private String tokenAddress;
    private int listCount;
    private List<ResponseDWalletBillEntry> bills;

    public ResponseDWalletBill() {
    }

    public ResponseDWalletBill(String tokenName, double tokenAmount, double tokenAmountToRmb, double lockedAmount, String tokenAddress, int listCount) {
        this.tokenName = tokenName;
        this.tokenAmount = tokenAmount;
        this.tokenAmountToRmb = tokenAmountToRmb;
        this.lockedAmount = lockedAmount;
        this.tokenAddress = tokenAddress;
        this.listCount = listCount;
    }

    public ResponseDWalletBill(String tokenName, double tokenAmount, double tokenAmountToRmb, double lockedAmount, String tokenAddress, int listCount, List<ResponseDWalletBillEntry> bills) {
        this.tokenName = tokenName;
        this.tokenAmount = tokenAmount;
        this.tokenAmountToRmb = tokenAmountToRmb;
        this.lockedAmount = lockedAmount;
        this.tokenAddress = tokenAddress;
        this.listCount = listCount;
        this.bills = bills;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public double getTokenAmount() {
        return tokenAmount;
    }

    public void setTokenAmount(double tokenAmount) {
        this.tokenAmount = tokenAmount;
    }

    public double getTokenAmountToRmb() {
        return tokenAmountToRmb;
    }

    public void setTokenAmountToRmb(double tokenAmountToRmb) {
        this.tokenAmountToRmb = tokenAmountToRmb;
    }

    public double getLockedAmount() {
        return lockedAmount;
    }

    public void setLockedAmount(double lockedAmount) {
        this.lockedAmount = lockedAmount;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public int getListCount() {
        return listCount;
    }

    public void setListCount(int listCount) {
        this.listCount = listCount;
    }

    public List<ResponseDWalletBillEntry> getBills() {
        return bills;
    }

    public void setBills(List<ResponseDWalletBillEntry> bills) {
        this.bills = bills;
    }
}
