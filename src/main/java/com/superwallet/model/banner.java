package com.superwallet.model;

public class banner {
    private Long bid;

    private byte[] photo;

    private String textOfAd;

    private String linkOfAd;

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

    public String getTextOfAd() {
        return textOfAd;
    }

    public void setTextOfAd(String textOfAd) {
        this.textOfAd = textOfAd;
    }

    public String getLinkOfAd() {
        return linkOfAd;
    }

    public void setLinkOfAd(String linkOfAd) {
        this.linkOfAd = linkOfAd;
    }
}