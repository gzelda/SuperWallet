package com.superwallet.pojo;

import java.util.Date;

public class Agent {
    private String uid;

    private Date createtime;

    private String nickname;

    private Byte sex;

    private String phonenumber;

    private Integer loweramount;

    private Double earnings;

    private Double totalincome;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber == null ? null : phonenumber.trim();
    }

    public Integer getLoweramount() {
        return loweramount;
    }

    public void setLoweramount(Integer loweramount) {
        this.loweramount = loweramount;
    }

    public Double getEarnings() {
        return earnings;
    }

    public void setEarnings(Double earnings) {
        this.earnings = earnings;
    }

    public Double getTotalincome() {
        return totalincome;
    }

    public void setTotalincome(Double totalincome) {
        this.totalincome = totalincome;
    }
}