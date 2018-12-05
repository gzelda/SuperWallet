package com.superwallet.pojo;

import java.io.Serializable;
import java.util.Date;

public class Transfer extends TransferKey implements Serializable {
    private String source;

    private String destination;

    private Double amount;

    private Byte transfertype;

    private Byte tokentype;

    private Date createdtime;

    private Byte status;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Byte getTransfertype() {
        return transfertype;
    }

    public void setTransfertype(Byte transfertype) {
        this.transfertype = transfertype;
    }

    public Byte getTokentype() {
        return tokentype;
    }

    public void setTokentype(Byte tokentype) {
        this.tokentype = tokentype;
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}