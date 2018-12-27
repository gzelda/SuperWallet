package com.superwallet.pojo;

import java.util.Date;

public class Profit extends ProfitKey {
    private String orderid;

    private Integer profittype;

    private Date createtime;

    private Double profit;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public Integer getProfittype() {
        return profittype;
    }

    public void setProfittype(Integer profittype) {
        this.profittype = profittype;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }
}