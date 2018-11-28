package com.superwallet.pojo;

public class Bgswallet {
    private String uid;

    private Integer lockedamount;

    private Integer availableamount;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
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
}