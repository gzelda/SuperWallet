package com.superwallet.pojo;

import java.io.Serializable;
import java.util.Date;

public class Userstatus implements Serializable {
    private String uid;

    private Byte state;

    private Date updatedtime;

    public Userstatus() {
    }

    public Userstatus(String uid, Byte state, Date updatedtime) {
        this.uid = uid;
        this.state = state;
        this.updatedtime = updatedtime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getUpdatedtime() {
        return updatedtime;
    }

    public void setUpdatedtime(Date updatedtime) {
        this.updatedtime = updatedtime;
    }
}