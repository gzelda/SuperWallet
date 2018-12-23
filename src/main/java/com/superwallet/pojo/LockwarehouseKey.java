package com.superwallet.pojo;

public class LockwarehouseKey {
    private Long lid;

    private String uid;

    public Long getLid() {
        return lid;
    }

    public void setLid(Long lid) {
        this.lid = lid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }
}