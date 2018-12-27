package com.superwallet.pojo;

public class Ethtoken extends EthtokenKey {
    private String ethaddress;

    private Double amount;

    private Byte canlock;

    public String getEthaddress() {
        return ethaddress;
    }

    public void setEthaddress(String ethaddress) {
        this.ethaddress = ethaddress == null ? null : ethaddress.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Byte getCanlock() {
        return canlock;
    }

    public void setCanlock(Byte canlock) {
        this.canlock = canlock;
    }
}