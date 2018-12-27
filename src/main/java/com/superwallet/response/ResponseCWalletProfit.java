package com.superwallet.response;

import java.io.Serializable;
import java.util.List;

public class ResponseCWalletProfit implements Serializable {
    private int listCount;
    private int isLocked;
    private int isAgent;
    private double tokenProfit;
    private double tokenProfitToRMB;
    private double lockedProfit;
    private double lockedProfitToRMB;
    private double agentProfit;
    private double agentProfitToRMB;
    private double mostRollOut;
    List<ResponseCWalletProfitEntry> list;

    public ResponseCWalletProfit() {
    }

    public ResponseCWalletProfit(int listCount, int isLocked, int isAgent, double tokenProfit, double tokenProfitToRMB, double lockedProfit, double lockedProfitToRMB, double agentProfit, double agentProfitToRMB, double mostRollOut, List<ResponseCWalletProfitEntry> list) {
        this.listCount = listCount;
        this.isLocked = isLocked;
        this.isAgent = isAgent;
        this.tokenProfit = tokenProfit;
        this.tokenProfitToRMB = tokenProfitToRMB;
        this.lockedProfit = lockedProfit;
        this.lockedProfitToRMB = lockedProfitToRMB;
        this.agentProfit = agentProfit;
        this.agentProfitToRMB = agentProfitToRMB;
        this.mostRollOut = mostRollOut;
        this.list = list;
    }

    public int getListCount() {
        return listCount;
    }

    public void setListCount(int listCount) {
        this.listCount = listCount;
    }

    public int getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(int isLocked) {
        this.isLocked = isLocked;
    }

    public int getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(int isAgent) {
        this.isAgent = isAgent;
    }

    public double getTokenProfit() {
        return tokenProfit;
    }

    public void setTokenProfit(double tokenProfit) {
        this.tokenProfit = tokenProfit;
    }

    public double getTokenProfitToRMB() {
        return tokenProfitToRMB;
    }

    public void setTokenProfitToRMB(double tokenProfitToRMB) {
        this.tokenProfitToRMB = tokenProfitToRMB;
    }

    public double getLockedProfit() {
        return lockedProfit;
    }

    public void setLockedProfit(double lockedProfit) {
        this.lockedProfit = lockedProfit;
    }

    public double getLockedProfitToRMB() {
        return lockedProfitToRMB;
    }

    public void setLockedProfitToRMB(double lockedProfitToRMB) {
        this.lockedProfitToRMB = lockedProfitToRMB;
    }

    public double getAgentProfit() {
        return agentProfit;
    }

    public void setAgentProfit(double agentProfit) {
        this.agentProfit = agentProfit;
    }

    public double getAgentProfitToRMB() {
        return agentProfitToRMB;
    }

    public void setAgentProfitToRMB(double agentProfitToRMB) {
        this.agentProfitToRMB = agentProfitToRMB;
    }

    public double getMostRollOut() {
        return mostRollOut;
    }

    public void setMostRollOut(double mostRollOut) {
        this.mostRollOut = mostRollOut;
    }

    public List<ResponseCWalletProfitEntry> getList() {
        return list;
    }

    public void setList(List<ResponseCWalletProfitEntry> list) {
        this.list = list;
    }
}
