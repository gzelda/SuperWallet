package com.superwallet.pojo;

public class LockwarehouseKey {
    private Long lockwarehouseid;

    private String uid;

    public Long getLockwarehouseid() {
        return lockwarehouseid;
    }

    public void setLockwarehouseid(Long lockwarehouseid) {
        this.lockwarehouseid = lockwarehouseid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }
}