package com.superwallet.pojo;

public class Preinviter {
    private String phonenum;

    private String invitedcode;

    public Preinviter() {
    }

    public Preinviter(String phonenum, String invitedcode) {
        this.phonenum = phonenum;
        this.invitedcode = invitedcode;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum == null ? null : phonenum.trim();
    }

    public String getInvitedcode() {
        return invitedcode;
    }

    public void setInvitedcode(String invitedcode) {
        this.invitedcode = invitedcode == null ? null : invitedcode.trim();
    }
}