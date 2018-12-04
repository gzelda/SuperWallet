package com.superwallet.pojo;

public class Ethtoken extends EthtokenKey {
    private String ethaddress;

    private Double lockedamount;

    private Double availableamount;

    private Double amount;

    public String getEthaddress() {
        return ethaddress;
    }

    public void setEthaddress(String ethaddress) {
        this.ethaddress = ethaddress == null ? null : ethaddress.trim();
    }

    public Double getLockedamount() {
        return lockedamount;
    }

    public void setLockedamount(Double lockedamount) {
        this.lockedamount = lockedamount;
    }

    public Double getAvailableamount() {
        return availableamount;
    }

    public void setAvailableamount(Double availableamount) {
        this.availableamount = availableamount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}