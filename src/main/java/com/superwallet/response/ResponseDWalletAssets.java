package com.superwallet.response;

import java.io.Serializable;
import java.util.List;

public class ResponseDWalletAssets implements Serializable {
    private double allTokenAmountToRMB;
    private int listCount;
    List<ResponseDWalletAssetsEntry> wallets;

    public ResponseDWalletAssets() {
    }

    public ResponseDWalletAssets(double allTokenAmountToRMB, int listCount) {
        this.allTokenAmountToRMB = allTokenAmountToRMB;
        this.listCount = listCount;
    }

    public ResponseDWalletAssets(double allTokenAmountToRMB, int listCount, List<ResponseDWalletAssetsEntry> wallets) {
        this.allTokenAmountToRMB = allTokenAmountToRMB;
        this.listCount = listCount;
        this.wallets = wallets;
    }

    public double getAllTokenAmountToRMB() {
        return allTokenAmountToRMB;
    }

    public void setAllTokenAmountToRMB(double allTokenAmountToRMB) {
        this.allTokenAmountToRMB = allTokenAmountToRMB;
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
