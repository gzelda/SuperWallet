package com.superwallet.common;

import java.io.Serializable;

public class RecordResult implements Serializable {
    private boolean isGenerated;
    private long transferId;

    public RecordResult() {
    }

    public RecordResult(boolean isGenerated, long transferId) {
        this.isGenerated = isGenerated;
        this.transferId = transferId;
    }

    public boolean isGenerated() {
        return isGenerated;
    }

    public void setGenerated(boolean generated) {
        isGenerated = generated;
    }

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }
}
