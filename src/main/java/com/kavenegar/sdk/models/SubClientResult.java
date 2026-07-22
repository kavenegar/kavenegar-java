package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubClientResult {

    @JsonProperty("remaincredit")
    private long remainCredit;

    @JsonProperty("expiredate")
    private long expireDate;

    @JsonProperty("lastlogindate")
    private long lastLoginDate;

    @JsonProperty("apikey")
    private String apiKey;

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("username")
    private String userName;

    @JsonProperty("pricingname")
    private String pricingName;

    @JsonProperty("smsfarsicost")
    private String smsFarsiCost;

    @JsonProperty("smsenglishcost")
    private String smsEnglishCost;

    @JsonProperty("calllocalcost")
    private String callLocalCost;

    @JsonProperty("assignedlines")
    private String assignedLines;

    @JsonProperty("status")
    private String status;

    @JsonProperty("passwordhashed")
    private String passwordHashed;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("localid")
    private String localId;

    @JsonProperty("minimumallowedcredit")
    private int minimumAllowedCredit;

    public SubClientResult() {}

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

    public long getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(long lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPricingName() {
        return pricingName;
    }

    public void setPricingName(String pricingName) {
        this.pricingName = pricingName;
    }

    public String getSmsFarsiCost() {
        return smsFarsiCost;
    }

    public void setSmsFarsiCost(String smsFarsiCost) {
        this.smsFarsiCost = smsFarsiCost;
    }

    public String getSmsEnglishCost() {
        return smsEnglishCost;
    }

    public void setSmsEnglishCost(String smsEnglishCost) {
        this.smsEnglishCost = smsEnglishCost;
    }

    public String getCallLocalCost() {
        return callLocalCost;
    }

    public void setCallLocalCost(String callLocalCost) {
        this.callLocalCost = callLocalCost;
    }

    public String getAssignedLines() {
        return assignedLines;
    }

    public void setAssignedLines(String assignedLines) {
        this.assignedLines = assignedLines;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPasswordHashed() {
        return passwordHashed;
    }

    public void setPasswordHashed(String passwordHashed) {
        this.passwordHashed = passwordHashed;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public int getMinimumAllowedCredit() {
        return minimumAllowedCredit;
    }

    public void setMinimumAllowedCredit(int minimumAllowedCredit) {
        this.minimumAllowedCredit = minimumAllowedCredit;
    }
}
