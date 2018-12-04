package com.superwallet.common;

import com.superwallet.pojo.Userbasic;

import java.io.Serializable;

public class LoginResult implements Serializable {
    private int code;
    private int status;
    private Userbasic user;

    public LoginResult() {
    }

    public LoginResult(int code, int status, Userbasic user) {
        this.code = code;
        this.status = status;
        this.user = user;
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

    public Userbasic getUser() {
        return user;
    }

    public void setUser(Userbasic user) {
        this.user = user;
    }
}