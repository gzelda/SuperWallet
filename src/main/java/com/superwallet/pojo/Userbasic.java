package com.superwallet.pojo;

import java.util.Date;

public class Userbasic {
    private String uid;

    private String nickname;

    private Byte sex;

    private Byte isagency;

    private String phonenumber;

    private String inviter;

    private Byte status;

    private String password;

    private String paypassword;

    private String invitedcode;

    private String invitedpeople;

    private Date registertime;

    private byte[] headphoto;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPaypassword() {
        return paypassword;
    }

    public void setPaypassword(String paypassword) {
        this.paypassword = paypassword == null ? null : paypassword.trim();
    }

    public String getInvitedcode() {
        return invitedcode;
    }

    public void setInvitedcode(String invitedcode) {
        this.invitedcode = invitedcode == null ? null : invitedcode.trim();
    }

    public String getInvitedpeople() {
        return invitedpeople;
    }

    public void setInvitedpeople(String invitedpeople) {
        this.invitedpeople = invitedpeople == null ? null : invitedpeople.trim();
    }

    public Date getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Date registertime) {
        this.registertime = registertime;
    }

    public byte[] getHeadphoto() {
        return headphoto;
    }

    public void setHeadphoto(byte[] headphoto) {
        this.headphoto = headphoto;
    }
}