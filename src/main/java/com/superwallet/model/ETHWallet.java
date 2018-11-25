package com.superwallet.model;

public class ETHWallet {
    private String uid;

    private String ethAddress;

    private Integer lockedAmount;

    private Integer availableAmount;

    private Integer amount;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEthAddress() {
        return ethAddress;
    }

    public void setEthAddress(String ethAddress) {
        this.ethAddress = ethAddress;
    }

    public Integer getLockedAmount() {
        return lockedAmount;
    }

    public void setLockedAmount(Integer lockedAmount) {
        this.lockedAmount = lockedAmount;
    }

    public Integer getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(Integer availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}