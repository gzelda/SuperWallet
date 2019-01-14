package com.superwallet.response;

import java.io.Serializable;
import java.util.List;

public class ResponseCWalletSimProfit implements Serializable {
    private double allTokenAmountToRMB;
    private double totalProfitToRMB;
    List<ResponseCWalletSimProfitEntry> list;

    public ResponseCWalletSimProfit() {
    }

    public ResponseCWalletSimProfit(double allTokenAmountToRMB, double totalProfitToRMB, List<ResponseCWalletSimProfitEntry> list) {
        this.allTokenAmountToRMB = allTokenAmountToRMB;
        this.totalProfitToRMB = totalProfitToRMB;
        this.list = list;
    }

    public double getAllTokenAmountToRMB() {
        return allTokenAmountToRMB;
    }

    public void setAllTokenAmountToRMB(double allTokenAmountToRMB) {
        this.allTokenAmountToRMB = allTokenAmountToRMB;
    }

    public List<ResponseCWalletSimProfitEntry> getList() {
        return list;
    }

    public void setList(List<ResponseCWalletSimProfitEntry> list) {
        this.list = list;
    }

    public double getTotalProfitToRMB() {
        return totalProfitToRMB;
    }

    public void setTotalProfitToRMB(double totalProfitToRMB) {
        this.totalProfitToRMB = totalProfitToRMB;
    }

}
