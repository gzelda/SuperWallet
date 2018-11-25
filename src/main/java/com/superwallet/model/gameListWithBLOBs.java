package com.superwallet.model;

public class gameListWithBLOBs extends gameList {
    private byte[] photo;

    private String text;

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}