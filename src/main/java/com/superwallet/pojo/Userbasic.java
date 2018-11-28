package com.superwallet.pojo;

public class Userbasic {
    private String uid;

    private String nackname;

    private Byte sex;

    private Byte isagency;

    private String phonenumber;

    private String inviter;

    private Byte status;

    private Object invitedpeople;

    private byte[] headphoto;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getNackname() {
        return nackname;
    }

    public void setNackname(String nackname) {
        this.nackname = nackname == null ? null : nackname.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getIsagency() {
        return isagency;
    }

    public void setIsagency(Byte isagency) {
        this.isagency = isagency;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber == null ? null : phonenumber.trim();
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter == null ? null : inviter.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Object getInvitedpeople() {
        return invitedpeople;
    }

    public void setInvitedpeople(Object invitedpeople) {
        this.invitedpeople = invitedpeople;
    }

    public byte[] getHeadphoto() {
        return headphoto;
    }

    public void setHeadphoto(byte[] headphoto) {
        this.headphoto = headphoto;
    }
}