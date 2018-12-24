package com.superwallet.response;

import java.io.Serializable;
import java.util.List;

public class ResponseDWalletBill implements Serializable {

    private String coinName;
    private double coinTotal;
    private double coinRmbTotal;
    private double lockCount;
    private String coinAddress;
    private int listCount;
    private List<ResponseDWalletBillEntry> bills;

    public ResponseDWalletBill() {
    }

    public ResponseDWalletBill(String coinName, double coinTotal, double coinRmbTotal, double lockCount, String coinAddress, int listCount, List<ResponseDWalletBillEntry> bills) {
        this.coinName = coinName;
        this.coinTotal = coinTotal;
        this.coinRmbTotal = coinRmbTotal;
        this.lockCount = lockCount;
        this.coinAddress = coinAddress;
        this.listCount = listCount;
        this.bills = bills;
    }

    public List<ResponseDWalletBillEntry> getBills() {
        return bills;
    }

    public void setBills(List<ResponseDWalletBillEntry> bills) {
        this.bills = bills;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
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

    public double getLockCount() {
        return lockCount;
    }

    public void setLockCount(double lockCount) {
        this.lockCount = lockCount;
    }

    public String getCoinAddress() {
        return coinAddress;
    }

    public void setCoinAddress(String coinAddress) {
        this.coinAddress = coinAddress;
    }

    public int getListCount() {
        return listCount;
    }

    public void setListCount(int listCount) {
        this.listCount = listCount;
    }
}
