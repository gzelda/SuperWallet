package com.superwallet.response;

import java.io.Serializable;

public class ResponseDWalletSimpleInfo implements Serializable {
    private int coinType;
    private String coinName;
    private String coinAddress;
    private String coinPrice;


    public ResponseDWalletSimpleInfo() {
    }

    public ResponseDWalletSimpleInfo(int coinType, String coinName, String coinAddress, String coinPrice) {
        this.coinType = coinType;
        this.coinName = coinName;
        this.coinAddress = coinAddress;
        this.coinPrice = coinPrice;
    }

    public int getCoinType() {
        return coinType;
    }

    public void setCoinType(int coinType) {
        this.coinType = coinType;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCoinAddress() {
        return coinAddress;
    }

    public void setCoinAddress(String coinAddress) {
        this.coinAddress = coinAddress;
    }

    public String getCoinPrice() {
        return coinPrice;
    }

    public void setCoinPrice(String coinPrice) {
        this.coinPrice = coinPrice;
    }
}
