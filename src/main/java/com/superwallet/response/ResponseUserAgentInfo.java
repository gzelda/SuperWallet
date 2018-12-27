package com.superwallet.response;

import java.io.Serializable;

public class ResponseUserAgentInfo implements Serializable {

    private double totalProfit;
    private int lowerAmount;
    private int yesterdayActiveCount;
    private double yesterdayProfit;
    private int totalAgentAmount;

    public ResponseUserAgentInfo() {
    }

    public ResponseUserAgentInfo(double totalProfit, int lowerAmount, int yesterdayActiveCount, double yesterdayProfit, int totalAgentAmount) {
        this.totalProfit = totalProfit;
        this.lowerAmount = lowerAmount;
        this.yesterdayActiveCount = yesterdayActiveCount;
        this.yesterdayProfit = yesterdayProfit;
        this.totalAgentAmount = totalAgentAmount;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public int getLowerAmount() {
        return lowerAmount;
    }

    public void setLowerAmount(int lowerAmount) {
        this.lowerAmount = lowerAmount;
    }

    public int getYesterdayActiveCount() {
        return yesterdayActiveCount;
    }

    public void setYesterdayActiveCount(int yesterdayActiveCount) {
        this.yesterdayActiveCount = yesterdayActiveCount;
    }

    public double getYesterdayProfit() {
        return yesterdayProfit;
    }

    public void setYesterdayProfit(double yesterdayProfit) {
        this.yesterdayProfit = yesterdayProfit;
    }

    public int getTotalAgentAmount() {
        return totalAgentAmount;
    }

    public void setTotalAgentAmount(int totalAgentAmount) {
        this.totalAgentAmount = totalAgentAmount;
    }
}
