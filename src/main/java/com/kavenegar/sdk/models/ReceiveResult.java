package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiveResult {

    @JsonProperty("messageid")
    private long messageId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("sender")
    private String sender;

    @JsonProperty("receptor")
    private String receptor;

    @JsonProperty("date")
    private long date;

    public ReceiveResult() {}

    public ReceiveResult(long messageId, String message, String sender, String receptor, long date) {
        this.messageId = messageId;
        this.message = message;
        this.sender = sender;
        this.receptor = receptor;
        this.date = date;
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
}
