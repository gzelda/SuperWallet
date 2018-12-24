package com.superwallet.response;

import java.io.Serializable;
import java.util.List;

public class ResponseDWalletAssets implements Serializable {
    private double allCoinTotal;
    private int listCount;
    List<ResponseDWalletAssetsEntry> wallets;

    public ResponseDWalletAssets() {
    }

    public ResponseDWalletAssets(double allCoinTotal, int listCount, List<ResponseDWalletAssetsEntry> wallets) {
        this.allCoinTotal = allCoinTotal;
        this.listCount = listCount;
        this.wallets = wallets;
    }

    public double getAllCoinTotal() {
        return allCoinTotal;
    }

    public void setAllCoinTotal(double allCoinTotal) {
        this.allCoinTotal = allCoinTotal;
    }

    public int getListCount() {
        return listCount;
    }

    public void setListCount(int listCount) {
        this.listCount = listCount;
    }

    public List<ResponseDWalletAssetsEntry> getWallets() {
        return wallets;
    }

    public void setWallets(List<ResponseDWalletAssetsEntry> wallets) {
        this.wallets = wallets;
    }
}
