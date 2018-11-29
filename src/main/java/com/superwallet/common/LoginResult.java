package com.superwallet.common;

import java.io.Serializable;

public class LoginResult implements Serializable {
    private int code;
    private int status;
    private String uid;

    public LoginResult() {
    }

    public LoginResult(int code, int status, String uid) {
        this.code = code;
        this.status = status;
        this.uid = uid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
