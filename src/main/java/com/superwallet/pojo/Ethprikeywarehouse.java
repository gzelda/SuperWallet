package com.superwallet.pojo;

public class Ethprikeywarehouse {
    private String uid;

    private String prikey;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getPrikey() {
        return prikey;
    }

    public void setPrikey(String prikey) {
        this.prikey = prikey == null ? null : prikey.trim();
    }
}