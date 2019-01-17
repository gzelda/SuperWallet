package com.superwallet.pojo;

public class Optconf {
    private String confname;

    private String confvalue;

    public String getConfname() {
        return confname;
    }

    public void setConfname(String confname) {
        this.confname = confname == null ? null : confname.trim();
    }

    public String getConfvalue() {
        return confvalue;
    }

    public void setConfvalue(String confvalue) {
        this.confvalue = confvalue == null ? null : confvalue.trim();
    }
}