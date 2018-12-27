package com.superwallet.response;

import java.io.Serializable;

public class ResponseContentEOSDetailInfo implements Serializable {

    private String mortgageCount;
    private String resourcesCount;
    private String canUseCount;
    private String netgageCount;
    private String networkRes;
    private String canUseNetwork;
    private String redemption;
    private String mortgageRemaining;
    private String redemptionRemaining;
    private String calculateMortgage;
    private String networkMortgage;
    private String calculateRedemption;
    private String networkRedemption;
    private String totalCount;
    private String availableCount;
    private String networkMemory;
    private String availableMemory;
    private String buyRemaining;
    private String soldRemaining;
    private String buyMemory;
    private String soldMemory;

    public ResponseContentEOSDetailInfo() {
    }

    public ResponseContentEOSDetailInfo(String mortgageCount, String resourcesCount, String canUseCount, String netgageCount, String networkRes, String canUseNetwork, String redemption, String mortgageRemaining, String redemptionRemaining, String calculateMortgage, String networkMortgage, String calculateRedemption, String networkRedemption, String totalCount, String availableCount, String networkMemory, String availableMemory, String buyRemaining, String soldRemaining, String buyMemory, String soldMemory) {
        this.mortgageCount = mortgageCount;
        this.resourcesCount = resourcesCount;
        this.canUseCount = canUseCount;
        this.netgageCount = netgageCount;
        this.networkRes = networkRes;
        this.canUseNetwork = canUseNetwork;
        this.redemption = redemption;
        this.mortgageRemaining = mortgageRemaining;
        this.redemptionRemaining = redemptionRemaining;
        this.calculateMortgage = calculateMortgage;
        this.networkMortgage = networkMortgage;
        this.calculateRedemption = calculateRedemption;
        this.networkRedemption = networkRedemption;
        this.totalCount = totalCount;
        this.availableCount = availableCount;
        this.networkMemory = networkMemory;
        this.availableMemory = availableMemory;
        this.buyRemaining = buyRemaining;
        this.soldRemaining = soldRemaining;
        this.buyMemory = buyMemory;
        this.soldMemory = soldMemory;
    }

    public String getMortgageCount() {
        return mortgageCount;
    }

    public void setMortgageCount(String mortgageCount) {
        this.mortgageCount = mortgageCount;
    }

    public String getResourcesCount() {
        return resourcesCount;
    }

    public void setResourcesCount(String resourcesCount) {
        this.resourcesCount = resourcesCount;
    }

    public String getCanUseCount() {
        return canUseCount;
    }

    public void setCanUseCount(String canUseCount) {
        this.canUseCount = canUseCount;
    }

    public String getNetgageCount() {
        return netgageCount;
    }

    public void setNetgageCount(String netgageCount) {
        this.netgageCount = netgageCount;
    }

    public String getNetworkRes() {
        return networkRes;
    }

    public void setNetworkRes(String networkRes) {
        this.networkRes = networkRes;
    }

    public String getCanUseNetwork() {
        return canUseNetwork;
    }

    public void setCanUseNetwork(String canUseNetwork) {
        this.canUseNetwork = canUseNetwork;
    }

    public String getRedemption() {
        return redemption;
    }

    public void setRedemption(String redemption) {
        this.redemption = redemption;
    }

    public String getMortgageRemaining() {
        return mortgageRemaining;
    }

    public void setMortgageRemaining(String mortgageRemaining) {
        this.mortgageRemaining = mortgageRemaining;
    }

    public String getRedemptionRemaining() {
        return redemptionRemaining;
    }

    public void setRedemptionRemaining(String redemptionRemaining) {
        this.redemptionRemaining = redemptionRemaining;
    }

    public String getCalculateMortgage() {
        return calculateMortgage;
    }

    public void setCalculateMortgage(String calculateMortgage) {
        this.calculateMortgage = calculateMortgage;
    }

    public String getNetworkMortgage() {
        return networkMortgage;
    }

    public void setNetworkMortgage(String networkMortgage) {
        this.networkMortgage = networkMortgage;
    }

    public String getCalculateRedemption() {
        return calculateRedemption;
    }

    public void setCalculateRedemption(String calculateRedemption) {
        this.calculateRedemption = calculateRedemption;
    }

    public String getNetworkRedemption() {
        return networkRedemption;
    }

    public void setNetworkRedemption(String networkRedemption) {
        this.networkRedemption = networkRedemption;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(String availableCount) {
        this.availableCount = availableCount;
    }

    public String getNetworkMemory() {
        return networkMemory;
    }

    public void setNetworkMemory(String networkMemory) {
        this.networkMemory = networkMemory;
    }

    public String getAvailableMemory() {
        return availableMemory;
    }

    public void setAvailableMemory(String availableMemory) {
        this.availableMemory = availableMemory;
    }

    public String getBuyRemaining() {
        return buyRemaining;
    }

    public void setBuyRemaining(String buyRemaining) {
        this.buyRemaining = buyRemaining;
    }

    public String getSoldRemaining() {
        return soldRemaining;
    }

    public void setSoldRemaining(String soldRemaining) {
        this.soldRemaining = soldRemaining;
    }

    public String getBuyMemory() {
        return buyMemory;
    }

    public void setBuyMemory(String buyMemory) {
        this.buyMemory = buyMemory;
    }

    public String getSoldMemory() {
        return soldMemory;
    }

    public void setSoldMemory(String soldMemory) {
        this.soldMemory = soldMemory;
    }
}
