package com.superwallet.pojo;

public class Eostoken extends EostokenKey {
    private String eosaccountname;

    private Double lockedamount;

    private Double availableamount;

    private Double amount;

    private Byte canlock;

    public String getEosaccountname() {
        return eosaccountname;
    }

    public void setEosaccountname(String eosaccountname) {
        this.eosaccountname = eosaccountname == null ? null : eosaccountname.trim();
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

    public Byte getCanlock() {
        return canlock;
    }

    public void setCanlock(Byte canlock) {
        this.canlock = canlock;
    }
}