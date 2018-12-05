package com.superwallet.pojo;

import java.io.Serializable;

public class Banner implements Serializable {
    private Long bid;

    private Integer type;

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}