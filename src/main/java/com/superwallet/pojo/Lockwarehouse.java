package com.superwallet.pojo;

import java.io.Serializable;
import java.util.Date;

public class Lockwarehouse extends LockwarehouseKey implements Serializable {
    private Double amount;

    private Integer period;

    private Date createdtime;

    private Byte status;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
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