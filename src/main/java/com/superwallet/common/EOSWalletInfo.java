package com.superwallet.common;

import java.io.Serializable;

public class EOSWalletInfo extends BasicWalletInfo implements Serializable {
    private String accountName;
    private double mortgageEOS_cpu;
    private double mortgageEOS_net;
    private double total_cpu;
    private double total_net;
    private double total_ram;
    private double used_cpu;
    private double used_net;
    private double used_ram;
    private double remain_cpu;
    private double reamin_net;
    private double EOSRAM;
    private double EOSRAM_USED;

    public String getAccountName() {
        return accountName;
    }

    public double getMortgageEOS_cpu() {
        return mortgageEOS_cpu;
    }

    public void setMortgageEOS_cpu(double mortgageEOS_cpu) {
        this.mortgageEOS_cpu = mortgageEOS_cpu;
    }

    public double getMortgageEOS_net() {
        return mortgageEOS_net;
    }

    public void setMortgageEOS_net(double mortgageEOS_net) {
        this.mortgageEOS_net = mortgageEOS_net;
    }

    public double getTotal_cpu() {
        return total_cpu;
    }

    public void setTotal_cpu(double total_cpu) {
        this.total_cpu = total_cpu;
    }

    public double getTotal_net() {
        return total_net;
    }

    public void setTotal_net(double total_net) {
        this.total_net = total_net;
    }

    public double getTotal_ram() {
        return total_ram;
    }

    public void setTotal_ram(double total_ram) {
        this.total_ram = total_ram;
    }

    public double getUsed_cpu() {
        return used_cpu;
    }

    public void setUsed_cpu(double used_cpu) {
        this.used_cpu = used_cpu;
    }

    public double getUsed_net() {
        return used_net;
    }

    public void setUsed_net(double used_net) {
        this.used_net = used_net;
    }

    public double getUsed_ram() {
        return used_ram;
    }

    public void setUsed_ram(double used_ram) {
        this.used_ram = used_ram;
    }

    public double getEOSRAM() {
        return EOSRAM;
    }

    public void setEOSRAM(double EOSRAM) {
        this.EOSRAM = EOSRAM;
    }

    public double getEOSRAM_USED() {
        return EOSRAM_USED;
    }

    public void setEOSRAM_USED(double EOSRAM_USED) {
        this.EOSRAM_USED = EOSRAM_USED;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public EOSWalletInfo() {
    }

    public EOSWalletInfo(double amount, double lockedAmount,
                         double availableAmount, double price,
                         byte canLock, String accountName,
                         double mortgageEOS_cpu, double mortgageEOS_net,
                         double total_cpu, double total_net,
                         double total_ram, double used_cpu,
                         double used_net, double used_ram,
                         double remain_cpu, double reamin_net
    ) {
        super(amount, lockedAmount, availableAmount, price, canLock);
        this.accountName = accountName;
        this.mortgageEOS_cpu = mortgageEOS_cpu;
        this.mortgageEOS_net = mortgageEOS_net;
        this.total_cpu = total_cpu;
        this.total_net = total_net;
        this.total_ram = total_ram;
        this.used_cpu = used_cpu;
        this.used_net = used_net;
        this.used_ram = used_ram;
        this.remain_cpu = remain_cpu;
        this.reamin_net = reamin_net;
    }
}
