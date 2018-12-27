package com.superwallet.pojo;

import java.util.Date;

public class Lockwarehouse extends LockwarehouseKey {
    private Double amount;

    private Integer period;

    private Date createdtime;

    private Integer tokentype;

    private Double finalprofit;

    private Integer status;

    private Integer profittokentype;

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

    public Integer getTokentype() {
        return tokentype;
    }

    public void setTokentype(Integer tokentype) {
        this.tokentype = tokentype;
    }

    public Double getFinalprofit() {
        return finalprofit;
    }

    public void setFinalprofit(Double finalprofit) {
        this.finalprofit = finalprofit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getProfittokentype() {
        return profittokentype;
    }

    public void setProfittokentype(Integer profittokentype) {
        this.profittokentype = profittokentype;
    }
}