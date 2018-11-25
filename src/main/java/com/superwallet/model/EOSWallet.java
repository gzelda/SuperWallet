package com.superwallet.model;

public class EOSWallet {
    private String uid;

    private String eosAddress;

    private Integer lockedAmount;

    private Integer availableAmount;

    private Integer amount;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEosAddress() {
        return eosAddress;
    }

    public void setEosAddress(String eosAddress) {
        this.eosAddress = eosAddress;
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