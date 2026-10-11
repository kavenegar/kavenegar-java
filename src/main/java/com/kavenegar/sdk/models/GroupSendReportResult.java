package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupSendReportResult {

    @JsonProperty("partyid")
    private int partyId;

    @JsonProperty("receptorscount")
    private int receptorsCount;

    @JsonProperty("messagecount")
    private int messageCount;

    @JsonProperty("approvalstatus")
    private String approvalStatus;

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private String status;

    @JsonProperty("messagelanguage")
    private String messageLanguage;

    @JsonProperty("sender")
    private String sender;

    @JsonProperty("date")
    private long date;

    public GroupSendReportResult() {}

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public int getReceptorsCount() {
        return receptorsCount;
    }

    public void setReceptorsCount(int receptorsCount) {
        this.receptorsCount = receptorsCount;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessageLanguage() {
        return messageLanguage;
    }

    public void setMessageLanguage(String messageLanguage) {
        this.messageLanguage = messageLanguage;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
