package com.superwallet.response;

import java.io.Serializable;
import java.util.List;

public class ResponseDWalletLockedOrder implements Serializable {
    private int listCount;
    List<ResponseDWalletLockedOrderEntry> orders;

    public ResponseDWalletLockedOrder() {
    }

    public ResponseDWalletLockedOrder(int listCount, List<ResponseDWalletLockedOrderEntry> orders) {
        this.listCount = listCount;
        this.orders = orders;
    }

    public int getListCount() {
        return listCount;
    }

    public void setListCount(int listCount) {
        this.listCount = listCount;
    }

    public List<ResponseDWalletLockedOrderEntry> getOrders() {
        return orders;
    }

    public void setOrders(List<ResponseDWalletLockedOrderEntry> orders) {
        this.orders = orders;
    }
}
