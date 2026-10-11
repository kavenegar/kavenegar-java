package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kavenegar.sdk.enums.MessageStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SendResult {

    @JsonProperty("messageid")
    private long messageId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private int status;

    @JsonProperty("statustext")
    private String statusText;

    @JsonProperty("sender")
    private String sender;

    @JsonProperty("receptor")
    private String receptor;

    @JsonProperty("date")
    private long date;

    @JsonProperty("cost")
    private int cost;

    public SendResult() {}

    public SendResult(long messageId, String message, int status, String statusText, String sender, String receptor, long date, int cost) {
        this.messageId = messageId;
        this.message = message;
        this.status = status;
        this.statusText = statusText;
        this.sender = sender;
        this.receptor = receptor;
        this.date = date;
        this.cost = cost;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public MessageStatus getMessageStatus() {
        return MessageStatus.fromValue(status);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}