package com.superwallet.common;

import java.io.Serializable;

public class ETHWalletInfo extends BasicWalletInfo implements Serializable {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ETHWalletInfo(String address, double amount, double lockedAmount, double availableAmount, double price, byte canLock) {
        super(amount, lockedAmount, availableAmount, price, canLock);
        this.address = address;
    }
}
