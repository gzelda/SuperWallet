package com.superwallet.pojo;

import java.io.Serializable;
import java.util.Date;

public class Notification extends NotificationKey implements Serializable {
    private String title;

    private Date createtime;

    private String notice;

    public Notification() {
    }

    public Notification(String uid, String title, String notice) {
        super(uid);
        this.title = title;
        this.notice = notice;
    }

    public Notification(String title, Date createtime, String notice) {
        this.title = title;
        this.createtime = createtime;
        this.notice = notice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice == null ? null : notice.trim();
    }
}