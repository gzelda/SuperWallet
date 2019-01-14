package com.superwallet.response;

import java.io.Serializable;

public class ResponseCWalletSimProfitEntry implements Serializable {
    private int tokenType;
    private String tokenName;
    private double tokenProfit;
    private double tokenProfitToRMB;

    public ResponseCWalletSimProfitEntry() {
    }

    public ResponseCWalletSimProfitEntry(int tokenType, String tokenName, double tokenProfit, double tokenProfitToRMB) {
        this.tokenType = tokenType;
        this.tokenName = tokenName;
        this.tokenProfit = tokenProfit;
        this.tokenProfitToRMB = tokenProfitToRMB;
    }

    public ResponseCWalletSimProfitEntry(String tokenName, double tokenProfit, double tokenProfitToRMB) {
        this.tokenName = tokenName;
        this.tokenProfit = tokenProfit;
        this.tokenProfitToRMB = tokenProfitToRMB;
    }

    public int getTokenType() {
        return tokenType;
    }

    public void setTokenType(int tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public double getTokenProfit() {
        return tokenProfit;
    }

    public void setTokenProfit(double tokenProfit) {
        this.tokenProfit = tokenProfit;
    }

    public double getTokenProfitToRMB() {
        return tokenProfitToRMB;
    }

    public void setTokenProfitToRMB(double tokenProfitToRMB) {
        this.tokenProfitToRMB = tokenProfitToRMB;
    }
}
