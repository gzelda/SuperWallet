package com.superwallet.pojo;

import java.util.Date;

public class Withdrawmoney extends WithdrawmoneyKey {
    private Byte tokentype;

    private Double amount;

    private Date createdtime;

    private Byte status;

    private String auditor;

    private Date audittime;

    private String remark;

    public Byte getTokentype() {
        return tokentype;
    }

    public void setTokentype(Byte tokentype) {
        this.tokentype = tokentype;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public Date getAudittime() {
        return audittime;
    }

    public void setAudittime(Date audittime) {
        this.audittime = audittime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}