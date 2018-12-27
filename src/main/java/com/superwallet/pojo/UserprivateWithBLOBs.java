package com.superwallet.pojo;

public class UserprivateWithBLOBs extends Userprivate {
    private byte[] idcardfront;

    private byte[] idcardback;

    private byte[] face;

    public byte[] getIdcardfront() {
        return idcardfront;
    }

    public void setIdcardfront(byte[] idcardfront) {
        this.idcardfront = idcardfront;
    }

    public byte[] getIdcardback() {
        return idcardback;
    }

    public void setIdcardback(byte[] idcardback) {
        this.idcardback = idcardback;
    }

    public byte[] getFace() {
        return face;
    }

    public void setFace(byte[] face) {
        this.face = face;
    }
}