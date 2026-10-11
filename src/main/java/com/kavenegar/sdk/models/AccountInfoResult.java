package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountInfoResult {

    @JsonProperty("remaincredit")
    private long remainCredit;

    @JsonProperty("expiredate")
    private long expireDate;

    @JsonProperty("type")
    private String type;

    public AccountInfoResult() {}

    public AccountInfoResult(long remainCredit, long expireDate, String type) {
        this.remainCredit = remainCredit;
        this.expireDate = expireDate;
        this.type = type;
    }

    public long getRemainCredit() {
        return remainCredit;
    }

    public void setRemainCredit(long remainCredit) {
        this.remainCredit = remainCredit;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
