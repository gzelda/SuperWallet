package com.superwallet.pojo;

import java.io.Serializable;

public class WithdrawmoneyKey implements Serializable {
    private String uid;

    private String wid;

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

    public WithdrawmoneyKey() {
    }

    public WithdrawmoneyKey(String uid, String wid) {
        this.uid = uid;
        this.wid = wid;
    }
}