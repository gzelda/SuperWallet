package com.superwallet.model;

import java.util.Date;

public class transfer extends transferKey {
    private String sourceAddress;

    private String destAddress;

    private Integer amount;

    private Integer transferType;

    private Byte tokenType;

    private Date createdTime;

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTransferType() {
        return transferType;
    }

    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }

    public Byte getTokenType() {
        return tokenType;
    }

    public void setTokenType(Byte tokenType) {
        this.tokenType = tokenType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}