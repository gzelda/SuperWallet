package com.superwallet.pojo;

import java.util.Date;

public class Userstatus {
    private String uid;

    private Date lastoptime;

    private String lastopdevice;

    private Date invitedtime;

    private Byte state;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Date getLastoptime() {
        return lastoptime;
    }

    public void setLastoptime(Date lastoptime) {
        this.lastoptime = lastoptime;
    }

    public String getLastopdevice() {
        return lastopdevice;
    }

    public void setLastopdevice(String lastopdevice) {
        this.lastopdevice = lastopdevice == null ? null : lastopdevice.trim();
    }

    public Date getInvitedtime() {
        return invitedtime;
    }

    public void setInvitedtime(Date invitedtime) {
        this.invitedtime = invitedtime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}