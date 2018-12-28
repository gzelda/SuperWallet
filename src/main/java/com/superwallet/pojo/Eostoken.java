package com.superwallet.pojo;

public class Eostoken extends EostokenKey {
    private String eosaccountname;

    private Double amount;

    private Byte canlock;

    public Eostoken() {
    }

    public Eostoken(String uid, Integer type, Double amount, Byte canlock) {
        super(uid, type);
        this.amount = amount;
        this.canlock = canlock;
    }

    public String getEosaccountname() {
        return eosaccountname;
    }

    public void setEosaccountname(String eosaccountname) {
        this.eosaccountname = eosaccountname == null ? null : eosaccountname.trim();
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