package com.superwallet.response;

import java.io.Serializable;

public class ResponseDWalletLockedOrderEntry implements Serializable {
    private String dealId;
    private String coinName;
    private String dealState;
    private double dealLockCount;
    private double dealInstanceProfit;
    private double dealTodayProfit;
    private int dealInstanceDay;
    private int dealTotalDay;
    private int dealLeftDay;
    private String dealStartTime;
    private String dealEndTime;
    private String dealProfitType;

    public ResponseDWalletLockedOrderEntry() {
    }

    public ResponseDWalletLockedOrderEntry(String dealId, String coinName,
                                           String dealState, double dealLockCount,
                                           double dealInstanceProfit, double dealTodayProfit,
                                           int dealInstanceDay, int dealTotalDay,
                                           int dealLeftDay, String dealStartTime,
                                           String dealEndTime, String dealProfitType) {
        this.dealId = dealId;
        this.coinName = coinName;
        this.dealState = dealState;
        this.dealLockCount = dealLockCount;
        this.dealInstanceProfit = dealInstanceProfit;
        this.dealTodayProfit = dealTodayProfit;
        this.dealInstanceDay = dealInstanceDay;
        this.dealTotalDay = dealTotalDay;
        this.dealLeftDay = dealLeftDay;
        this.dealStartTime = dealStartTime;
        this.dealEndTime = dealEndTime;
        this.dealProfitType = dealProfitType;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getDealState() {
        return dealState;
    }

    public void setDealState(String dealState) {
        this.dealState = dealState;
    }

    public double getDealLockCount() {
        return dealLockCount;
    }

    public void setDealLockCount(double dealLockCount) {
        this.dealLockCount = dealLockCount;
    }

    public double getDealInstanceProfit() {
        return dealInstanceProfit;
    }

    public void setDealInstanceProfit(double dealInstanceProfit) {
        this.dealInstanceProfit = dealInstanceProfit;
    }

    public double getDealTodayProfit() {
        return dealTodayProfit;
    }

    public void setDealTodayProfit(double dealTodayProfit) {
        this.dealTodayProfit = dealTodayProfit;
    }

    public int getDealInstanceDay() {
        return dealInstanceDay;
    }

    public void setDealInstanceDay(int dealInstanceDay) {
        this.dealInstanceDay = dealInstanceDay;
    }

    public int getDealTotalDay() {
        return dealTotalDay;
    }

    public void setDealTotalDay(int dealTotalDay) {
        this.dealTotalDay = dealTotalDay;
    }

    public int getDealLeftDay() {
        return dealLeftDay;
    }

    public void setDealLeftDay(int dealLeftDay) {
        this.dealLeftDay = dealLeftDay;
    }

    public String getDealStartTime() {
        return dealStartTime;
    }

    public void setDealStartTime(String dealStartTime) {
        this.dealStartTime = dealStartTime;
    }

    public String getDealEndTime() {
        return dealEndTime;
    }

    public void setDealEndTime(String dealEndTime) {
        this.dealEndTime = dealEndTime;
    }

    public String getDealProfitType() {
        return dealProfitType;
    }

    public void setDealProfitType(String dealProfitType) {
        this.dealProfitType = dealProfitType;
    }
}
