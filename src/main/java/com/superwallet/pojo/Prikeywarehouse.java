package com.superwallet.pojo;

public class Prikeywarehouse {
    private String uid;

    private Byte keytype;

    private String prikey;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Byte getKeytype() {
        return keytype;
    }

    public void setKeytype(Byte keytype) {
        this.keytype = keytype;
    }

    public String getPrikey() {
        return prikey;
    }

    public void setPrikey(String prikey) {
        this.prikey = prikey == null ? null : prikey.trim();
    }
}