package com.superwallet.response;

import java.io.Serializable;

public class ResponseDWalletSimpleInfo implements Serializable {
    private int tokenType;
    private String tokenName;
    private String tokenAddress;
    private double tokenPriceToRMB;


    public ResponseDWalletSimpleInfo() {
    }

    public ResponseDWalletSimpleInfo(int tokenType, String tokenName, String tokenAddress, double tokenPriceToRMB) {
        this.tokenType = tokenType;
        this.tokenName = tokenName;
        this.tokenAddress = tokenAddress;
        this.tokenPriceToRMB = tokenPriceToRMB;
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

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public double getTokenPriceToRMB() {
        return tokenPriceToRMB;
    }

    public void setTokenPriceToRMB(double tokenPriceToRMB) {
        this.tokenPriceToRMB = tokenPriceToRMB;
    }
}
