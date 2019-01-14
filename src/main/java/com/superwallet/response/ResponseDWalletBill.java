package com.superwallet.response;

import com.superwallet.common.CommonWalletInfo;

import java.io.Serializable;
import java.util.List;

public class ResponseDWalletBill implements Serializable {

    private List<CommonWalletInfo> wallets;
    private int listCount;
    private List<ResponseDWalletBillEntry> bills;

    public ResponseDWalletBill() {
    }

    public ResponseDWalletBill(List<CommonWalletInfo> wallets, int listCount, List<ResponseDWalletBillEntry> bills) {
        this.wallets = wallets;
        this.listCount = listCount;
        this.bills = bills;
    }

    public List<CommonWalletInfo> getWallets() {
        return wallets;
    }

    public void setWallets(List<CommonWalletInfo> wallets) {
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
