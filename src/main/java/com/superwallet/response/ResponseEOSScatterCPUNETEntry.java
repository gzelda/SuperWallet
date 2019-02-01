package com.superwallet.response;

import java.io.Serializable;

public class ResponseEOSScatterCPUNETEntry implements Serializable {
    private double CPU_remain_percent;
    private double NET_remain_percent;

    public ResponseEOSScatterCPUNETEntry(double CPU_remain_percent, double NET_remain_percent) {
        this.CPU_remain_percent = CPU_remain_percent;
        this.NET_remain_percent = NET_remain_percent;
    }

    public double getCPU_remain_percent() {
        return CPU_remain_percent;
    }

    public void setCPU_remain_percent(double CPU_remain_percent) {
        this.CPU_remain_percent = CPU_remain_percent;
    }

    public double getNET_remain_percent() {
        return NET_remain_percent;
    }

    public void setNET_remain_percent(double NET_remain_percent) {
        this.NET_remain_percent = NET_remain_percent;
    }
}
