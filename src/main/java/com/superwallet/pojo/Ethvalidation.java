package com.superwallet.pojo;

public class Ethvalidation extends EthvalidationKey {
    private String hashvalue;

    private Integer status;

    public String getHashvalue() {
        return hashvalue;
    }

    public void setHashvalue(String hashvalue) {
        this.hashvalue = hashvalue == null ? null : hashvalue.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}