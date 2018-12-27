package com.superwallet.pojo;

public class InviterKey {
    private String inviterid;

    private String beinvitedid;

    public String getInviterid() {
        return inviterid;
    }

    public void setInviterid(String inviterid) {
        this.inviterid = inviterid == null ? null : inviterid.trim();
    }

    public String getBeinvitedid() {
        return beinvitedid;
    }

    public void setBeinvitedid(String beinvitedid) {
        this.beinvitedid = beinvitedid == null ? null : beinvitedid.trim();
    }
}