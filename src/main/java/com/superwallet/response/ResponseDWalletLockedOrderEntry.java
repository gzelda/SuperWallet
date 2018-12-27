package com.superwallet.response;

import java.io.Serializable;

public class ResponseDWalletLockedOrderEntry implements Serializable {
    private String LID;
    private String lockedOrderProfitTokenName;
    private double lockedOrderTodayProfitToRMB;
    private double lockedAmount;
    private double lockedOrderInTimeProfit;
    private String lockedOrderStartTime;
    private String lockedOrderEndTime;
    private int period;
    private int lockedOrderLeftDay;
    private String lockedOrderState;
    private String tokenName;

    public ResponseDWalletLockedOrderEntry() {
    }

    public ResponseDWalletLockedOrderEntry(String LID, String lockedOrderProfitTokenName, double lockedOrderTodayProfitToRMB, double lockedAmount, double lockedOrderInTimeProfit, String lockedOrderStartTime, String lockedOrderEndTime, int period, int lockedOrderLeftDay, String lockedOrderState, String tokenName) {
        this.LID = LID;
        this.lockedOrderProfitTokenName = lockedOrderProfitTokenName;
        this.lockedOrderTodayProfitToRMB = lockedOrderTodayProfitToRMB;
        this.lockedAmount = lockedAmount;
        this.lockedOrderInTimeProfit = lockedOrderInTimeProfit;
        this.lockedOrderStartTime = lockedOrderStartTime;
        this.lockedOrderEndTime = lockedOrderEndTime;
        this.period = period;
        this.lockedOrderLeftDay = lockedOrderLeftDay;
        this.lockedOrderState = lockedOrderState;
        this.tokenName = tokenName;
    }

    public String getLID() {
        return LID;
    }

    public void setLID(String LID) {
        this.LID = LID;
    }

    public String getLockedOrderProfitTokenName() {
        return lockedOrderProfitTokenName;
    }

    public void setLockedOrderProfitTokenName(String lockedOrderProfitTokenName) {
        this.lockedOrderProfitTokenName = lockedOrderProfitTokenName;
    }

    public double getLockedOrderTodayProfitToRMB() {
        return lockedOrderTodayProfitToRMB;
    }

    public void setLockedOrderTodayProfitToRMB(double lockedOrderTodayProfitToRMB) {
        this.lockedOrderTodayProfitToRMB = lockedOrderTodayProfitToRMB;
    }

    public double getLockedAmount() {
        return lockedAmount;
    }

    public void setLockedAmount(double lockedAmount) {
        this.lockedAmount = lockedAmount;
    }

    public double getLockedOrderInTimeProfit() {
        return lockedOrderInTimeProfit;
    }

    public void setLockedOrderInTimeProfit(double lockedOrderInTimeProfit) {
        this.lockedOrderInTimeProfit = lockedOrderInTimeProfit;
    }

    public String getLockedOrderStartTime() {
        return lockedOrderStartTime;
    }

    public void setLockedOrderStartTime(String lockedOrderStartTime) {
        this.lockedOrderStartTime = lockedOrderStartTime;
    }

    public String getLockedOrderEndTime() {
        return lockedOrderEndTime;
    }

    public void setLockedOrderEndTime(String lockedOrderEndTime) {
        this.lockedOrderEndTime = lockedOrderEndTime;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getLockedOrderLeftDay() {
        return lockedOrderLeftDay;
    }

    public void setLockedOrderLeftDay(int lockedOrderLeftDay) {
        this.lockedOrderLeftDay = lockedOrderLeftDay;
    }

    public String getLockedOrderState() {
        return lockedOrderState;
    }

    public void setLockedOrderState(String lockedOrderState) {
        this.lockedOrderState = lockedOrderState;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }
}
