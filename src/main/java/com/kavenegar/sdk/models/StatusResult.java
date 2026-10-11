package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kavenegar.sdk.enums.MessageStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusResult {

    @JsonProperty("messageid")
    private long messageId;

    @JsonProperty("status")
    private int status;

    @JsonProperty("statustext")
    private String statusText;

    @JsonProperty("receptor")
    private String receptor;

    public StatusResult() {}

    public StatusResult(long messageId, int status, String statusText, String receptor) {
        this.messageId = messageId;
        this.status = status;
        this.statusText = statusText;
        this.receptor = receptor;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
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

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }
}
