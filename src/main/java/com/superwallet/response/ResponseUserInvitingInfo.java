package com.superwallet.response;

import java.io.Serializable;

public class ResponseUserInvitingInfo implements Serializable {

    private String invitedCode;
    private int hasInvitedPeopleCount;
    private double bgsHasGot;
    private String inviteUrl;

    public ResponseUserInvitingInfo(String invitedCode, int hasInvitedPeopleCount, double bgsHasGot, String inviteUrl) {
        this.invitedCode = invitedCode;
        this.hasInvitedPeopleCount = hasInvitedPeopleCount;
        this.bgsHasGot = bgsHasGot;
        this.inviteUrl = inviteUrl;
    }

    public String getInvitedCode() {
        return invitedCode;
    }

    public void setInvitedCode(String invitedCode) {
        this.invitedCode = invitedCode;
    }

    public int getHasInvitedPeopleCount() {
        return hasInvitedPeopleCount;
    }

    public void setHasInvitedPeopleCount(int hasInvitedPeopleCount) {
        this.hasInvitedPeopleCount = hasInvitedPeopleCount;
    }

    public double getBgsHasGot() {
        return bgsHasGot;
    }

    public void setBgsHasGot(double bgsHasGot) {
        this.bgsHasGot = bgsHasGot;
    }

    public String getInviteUrl() {
        return inviteUrl;
    }

    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
    }
}
