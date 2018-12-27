package com.superwallet.response;

import java.io.Serializable;
import java.util.List;

public class ResponseCWalletSimProfit implements Serializable {

    private double totalProfitToRMB;
    List<ResponseCWalletSimProfitEntry> list;

    public ResponseCWalletSimProfit() {
    }

    public ResponseCWalletSimProfit(double totalProfitToRMB, List<ResponseCWalletSimProfitEntry> list) {
        this.totalProfitToRMB = totalProfitToRMB;
        this.list = list;
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
