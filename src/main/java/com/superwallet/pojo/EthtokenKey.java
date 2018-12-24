package com.superwallet.pojo;

public class EthtokenKey {
    private String uid;

    private Integer type;

    public EthtokenKey() {
    }

    public EthtokenKey(String uid, Integer type) {
        this.uid = uid;
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}