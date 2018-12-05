package com.superwallet.pojo;

import java.io.Serializable;

public class EthtokenKey implements Serializable {
    private String uid;

    private Integer type;

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