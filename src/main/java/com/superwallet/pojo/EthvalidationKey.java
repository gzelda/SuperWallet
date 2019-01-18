package com.superwallet.pojo;

public class EthvalidationKey {
    private String uid;

    private Long transferid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Long getTransferid() {
        return transferid;
    }

    public void setTransferid(Long transferid) {
        this.transferid = transferid;
    }
}