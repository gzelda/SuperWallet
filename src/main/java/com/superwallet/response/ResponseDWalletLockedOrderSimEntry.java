package com.superwallet.response;

import java.io.Serializable;

public class ResponseDWalletLockedOrderSimEntry implements Serializable {
    private String LID;
    private String tokenName;
    private String lockedOrderState;
    private double lockedOrderInTimeProfit;
    private double lockedDay;
    private double period;
    private String lockedOrderStartTime;

    public ResponseDWalletLockedOrderSimEntry() {
    }

    public ResponseDWalletLockedOrderSimEntry(String LID, String tokenName, String lockedOrderState, double lockedOrderInTimeProfit, double lockedDay, double period, String lockedOrderStartTime) {
        this.LID = LID;
        this.tokenName = tokenName;
        this.lockedOrderState = lockedOrderState;
        this.lockedOrderInTimeProfit = lockedOrderInTimeProfit;
        this.lockedDay = lockedDay;
        this.period = period;
        this.lockedOrderStartTime = lockedOrderStartTime;
    }

    public String getLID() {
        return LID;
    }

    public void setLID(String LID) {
        this.LID = LID;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getLockedOrderState() {
        return lockedOrderState;
    }

    public void setLockedOrderState(String lockedOrderState) {
        this.lockedOrderState = lockedOrderState;
    }

    public double getLockedOrderInTimeProfit() {
        return lockedOrderInTimeProfit;
    }

    public void setLockedOrderInTimeProfit(double lockedOrderInTimeProfit) {
        this.lockedOrderInTimeProfit = lockedOrderInTimeProfit;
    }

    public double getLockedDay() {
        return lockedDay;
    }

    public void setLockedDay(double lockedDay) {
        this.lockedDay = lockedDay;
    }

    public double getPeriod() {
        return period;
    }

    public void setPeriod(double period) {
        this.period = period;
    }

    public String getLockedOrderStartTime() {
        return lockedOrderStartTime;
    }

    public void setLockedOrderStartTime(String lockedOrderStartTime) {
        this.lockedOrderStartTime = lockedOrderStartTime;
    }
}
