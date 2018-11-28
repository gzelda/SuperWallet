package com.superwallet.pojo;

public class Banner {
    private Long bid;

    private byte[] photo;

    private String textofad;

    private String linkofad;

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getTextofad() {
        return textofad;
    }

    public void setTextofad(String textofad) {
        this.textofad = textofad == null ? null : textofad.trim();
    }

    public String getLinkofad() {
        return linkofad;
    }

    public void setLinkofad(String linkofad) {
        this.linkofad = linkofad == null ? null : linkofad.trim();
    }
}