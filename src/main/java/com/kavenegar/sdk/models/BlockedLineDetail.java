package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockedLineDetail {

    @JsonProperty("number")
    private String number;

    @JsonProperty("blockreason")
    private byte blockReason;

    @JsonProperty("date")
    private long date;

    public BlockedLineDetail() {}

    public BlockedLineDetail(String number, byte blockReason, long date) {
        this.number = number;
        this.blockReason = blockReason;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public byte getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(byte blockReason) {
        this.blockReason = blockReason;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
