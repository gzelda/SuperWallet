package com.superwallet.pojo;

public class WithdrawmoneyKey {
    private String uid;

    private String wid;

    public WithdrawmoneyKey(String uid, String wid) {
        this.uid = uid;
        this.wid = wid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid == null ? null : wid.trim();
    }
}