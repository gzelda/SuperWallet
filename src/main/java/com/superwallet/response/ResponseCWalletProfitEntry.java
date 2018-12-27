package com.superwallet.response;

import java.io.Serializable;

public class ResponseCWalletProfitEntry implements Serializable {

    private String type;
    private String time;
    private int isFinished;
    private String status;
    private double profit;
    private double profitToRMB;

    public ResponseCWalletProfitEntry() {
    }

    public ResponseCWalletProfitEntry(String type, String time, int isFinished, String status, double profit, double profitToRMB) {
        this.type = type;
        this.time = time;
        this.isFinished = isFinished;
        this.status = status;
        this.profit = profit;
        this.profitToRMB = profitToRMB;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getProfitToRMB() {
        return profitToRMB;
    }

    public void setProfitToRMB(double profitToRMB) {
        this.profitToRMB = profitToRMB;
    }
}
