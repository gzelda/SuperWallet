package com.superwallet.model;

public class BGSWallet {
    private String uid;

    private Integer lockedAmount;

    private Integer availableAmount;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
}