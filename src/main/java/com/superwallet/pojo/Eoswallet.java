package com.superwallet.pojo;

public class Eoswallet {
    private String uid;

    private String eosaddress;

    private Integer lockedamount;

    private Integer availableamount;

    private Integer amount;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getEosaddress() {
        return eosaddress;
    }

    public void setEosaddress(String eosaddress) {
        this.eosaddress = eosaddress == null ? null : eosaddress.trim();
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