package com.superwallet.pojo;

import java.util.Date;

public class Transfer extends TransferKey {
    private String sourceaddress;

    private String destaddress;

    private Integer amount;

    private Integer transfertype;

    private Byte tokentype;

    private Date createdtime;

    public String getSourceaddress() {
        return sourceaddress;
    }

    public void setSourceaddress(String sourceaddress) {
        this.sourceaddress = sourceaddress == null ? null : sourceaddress.trim();
    }

    public String getDestaddress() {
        return destaddress;
    }

    public void setDestaddress(String destaddress) {
        this.destaddress = destaddress == null ? null : destaddress.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTransfertype() {
        return transfertype;
    }

    public void setTransfertype(Integer transfertype) {
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
}