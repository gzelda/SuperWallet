package com.superwallet.model;

public class userBasic {
    private String uid;

    private String nackName;

    private Byte sex;

    private Byte isAgency;

    private String phoneNumber;

    private String inviter;

    private Byte status;

    private String invitedPeople;

    private byte[] headPhoto;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNackName() {
        return nackName;
    }

    public void setNackName(String nackName) {
        this.nackName = nackName;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getIsAgency() {
        return isAgency;
    }

    public void setIsAgency(Byte isAgency) {
        this.isAgency = isAgency;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getInvitedPeople() {
        return invitedPeople;
    }

    public void setInvitedPeople(String invitedPeople) {
        this.invitedPeople = invitedPeople;
    }

    public byte[] getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(byte[] headPhoto) {
        this.headPhoto = headPhoto;
    }
}