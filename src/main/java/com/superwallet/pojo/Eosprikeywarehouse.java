package com.superwallet.pojo;

public class Eosprikeywarehouse {
    private String uid;

    private String ownerprikey;

    private String activeprikey;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getOwnerprikey() {
        return ownerprikey;
    }

    public void setOwnerprikey(String ownerprikey) {
        this.ownerprikey = ownerprikey == null ? null : ownerprikey.trim();
    }

    public String getActiveprikey() {
        return activeprikey;
    }

    public void setActiveprikey(String activeprikey) {
        this.activeprikey = activeprikey == null ? null : activeprikey.trim();
    }
}