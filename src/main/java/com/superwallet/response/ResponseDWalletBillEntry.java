package com.superwallet.response;

import java.io.Serializable;

public class ResponseDWalletBillEntry implements Serializable {
    private String dealType;
    private String dealState;
    private double dealCount;
    private double dealRmbCount;
    private String dealTime;

    public ResponseDWalletBillEntry() {
    }

    public ResponseDWalletBillEntry(String dealType, String dealState, double dealCount, double dealRmbCount, String dealTime) {
        this.dealType = dealType;
        this.dealState = dealState;
        this.dealCount = dealCount;
        this.dealRmbCount = dealRmbCount;
        this.dealTime = dealTime;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getDealState() {
        return dealState;
    }

    public void setDealState(String dealState) {
        this.dealState = dealState;
    }

    public double getDealCount() {
        return dealCount;
    }

    public void setDealCount(double dealCount) {
        this.dealCount = dealCount;
    }

    public double getDealRmbCount() {
        return dealRmbCount;
    }

    public void setDealRmbCount(double dealRmbCount) {
        this.dealRmbCount = dealRmbCount;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }
}
