package com.superwallet.pojo;

public class Ethwallet {
    private String uid;

    private String ethaddress;

    private Integer lockedamount;

    private Integer availableamount;

    private Integer amount;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getEthaddress() {
        return ethaddress;
    }

    public void setEthaddress(String ethaddress) {
        this.ethaddress = ethaddress == null ? null : ethaddress.trim();
    }

    public Integer getLockedamount() {
        return lockedamount;
    }

    public void setLockedamount(Integer lockedamount) {
        this.lockedamount = lockedamount;
    }

    public Integer getAvailableamount() {
        return availableamount;
    }

    public void setAvailableamount(Integer availableamount) {
        this.availableamount = availableamount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}