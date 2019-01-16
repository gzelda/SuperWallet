package com.superwallet.pojo;

public class NotificationKey {
    private Long nid;

    private String uid;

    public NotificationKey() {
    }

    public NotificationKey(String uid) {
        this.uid = uid;
    }

    public Long getNid() {
        return nid;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }
}