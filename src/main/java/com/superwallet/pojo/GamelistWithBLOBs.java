package com.superwallet.pojo;

import java.io.Serializable;

public class GamelistWithBLOBs extends Gamelist implements Serializable {
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
        this.text = text == null ? null : text.trim();
    }
}