package com.superwallet.pojo;

import java.io.Serializable;

public class TransferKey implements Serializable {
    private Long transferid;

    private String uid;

    public Long getTransferid() {
        return transferid;
    }

    public void setTransferid(Long transferid) {
        this.transferid = transferid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }
}