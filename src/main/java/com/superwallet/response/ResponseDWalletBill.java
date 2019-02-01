package com.superwallet.response;

import com.superwallet.common.CommonWalletInfo;

import java.io.Serializable;
import java.util.List;

public class ResponseDWalletBill implements Serializable {

    private List<ResponseDWalletBillWalletEntry> wallets;
    private int listCount;
    private List<ResponseDWalletBillEntry> bills;

    public ResponseDWalletBill() {
    }

    public ResponseDWalletBill(List<ResponseDWalletBillWalletEntry> wallets, int listCount, List<ResponseDWalletBillEntry> bills) {
        this.wallets = wallets;
        this.listCount = listCount;
        this.bills = bills;
    }

    public List<ResponseDWalletBillWalletEntry> getWallets() {
        return wallets;
    }

    public void setWallets(List<ResponseDWalletBillWalletEntry> wallets) {
        this.wallets = wallets;
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
