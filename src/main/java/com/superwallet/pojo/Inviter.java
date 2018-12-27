package com.superwallet.pojo;

import java.util.Date;

public class Inviter extends InviterKey {
    private Date invitingtime;

    public Date getInvitingtime() {
        return invitingtime;
    }

    public void setInvitingtime(Date invitingtime) {
        this.invitingtime = invitingtime;
    }
}