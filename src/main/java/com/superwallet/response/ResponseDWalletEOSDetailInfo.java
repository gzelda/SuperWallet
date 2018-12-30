package com.superwallet.response;

import java.io.Serializable;

public class ResponseDWalletEOSDetailInfo implements Serializable {
    private String mortgageEOS_cpu;
    private String used_cpu;
    private String total_cpu;
    private String mortgageEOS_net;
    private String used_net;
    private String total_net;
    private String balance;
    private String price_cpu;
    private String price_net;
    private String cpu_can_redemption;
    private String net_can_redemption;
    private String used_ram;
    private String total_ram;
    private String price_ram;
    private String used_ram_chain;
    private String total_ram_chain;

    public ResponseDWalletEOSDetailInfo() {
    }

    public ResponseDWalletEOSDetailInfo(String mortgageEOS_cpu, String used_cpu, String total_cpu, String mortgageEOS_net, String used_net, String total_net, String balance, String price_cpu, String price_net, String cpu_can_redemption, String net_can_redemption, String used_ram, String total_ram, String price_ram, String used_ram_chain, String total_ram_chain) {
        this.mortgageEOS_cpu = mortgageEOS_cpu;
        this.used_cpu = used_cpu;
        this.total_cpu = total_cpu;
        this.mortgageEOS_net = mortgageEOS_net;
        this.used_net = used_net;
        this.total_net = total_net;
        this.balance = balance;
        this.price_cpu = price_cpu;
        this.price_net = price_net;
        this.cpu_can_redemption = cpu_can_redemption;
        this.net_can_redemption = net_can_redemption;
        this.used_ram = used_ram;
        this.total_ram = total_ram;
        this.price_ram = price_ram;
        this.used_ram_chain = used_ram_chain;
        this.total_ram_chain = total_ram_chain;
    }

    public String getMortgageEOS_cpu() {
        return mortgageEOS_cpu;
    }

    public void setMortgageEOS_cpu(String mortgageEOS_cpu) {
        this.mortgageEOS_cpu = mortgageEOS_cpu;
    }

    public String getUsed_cpu() {
        return used_cpu;
    }

    public void setUsed_cpu(String used_cpu) {
        this.used_cpu = used_cpu;
    }

    public String getTotal_cpu() {
        return total_cpu;
    }

    public void setTotal_cpu(String total_cpu) {
        this.total_cpu = total_cpu;
    }

    public String getMortgageEOS_net() {
        return mortgageEOS_net;
    }

    public void setMortgageEOS_net(String mortgageEOS_net) {
        this.mortgageEOS_net = mortgageEOS_net;
    }

    public String getUsed_net() {
        return used_net;
    }

    public void setUsed_net(String used_net) {
        this.used_net = used_net;
    }

    public String getTotal_net() {
        return total_net;
    }

    public void setTotal_net(String total_net) {
        this.total_net = total_net;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPrice_cpu() {
        return price_cpu;
    }

    public void setPrice_cpu(String price_cpu) {
        this.price_cpu = price_cpu;
    }

    public String getPrice_net() {
        return price_net;
    }

    public void setPrice_net(String price_net) {
        this.price_net = price_net;
    }

    public String getCpu_can_redemption() {
        return cpu_can_redemption;
    }

    public void setCpu_can_redemption(String cpu_can_redemption) {
        this.cpu_can_redemption = cpu_can_redemption;
    }

    public String getNet_can_redemption() {
        return net_can_redemption;
    }

    public void setNet_can_redemption(String net_can_redemption) {
        this.net_can_redemption = net_can_redemption;
    }

    public String getUsed_ram() {
        return used_ram;
    }

    public void setUsed_ram(String used_ram) {
        this.used_ram = used_ram;
    }

    public String getTotal_ram() {
        return total_ram;
    }

    public void setTotal_ram(String total_ram) {
        this.total_ram = total_ram;
    }

    public String getPrice_ram() {
        return price_ram;
    }

    public void setPrice_ram(String price_ram) {
        this.price_ram = price_ram;
    }

    public String getUsed_ram_chain() {
        return used_ram_chain;
    }

    public void setUsed_ram_chain(String used_ram_chain) {
        this.used_ram_chain = used_ram_chain;
    }

    public String getTotal_ram_chain() {
        return total_ram_chain;
    }

    public void setTotal_ram_chain(String total_ram_chain) {
        this.total_ram_chain = total_ram_chain;
    }
}
