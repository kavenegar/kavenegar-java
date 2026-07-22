package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kavenegar.sdk.enums.MessageStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusLocalMessageIdResult {

    @JsonProperty("messageid")
    private long messageId;

    @JsonProperty("localid")
    private String localId;

    @JsonProperty("status")
    private int status;

    @JsonProperty("statustext")
    private String statusText;

    public StatusLocalMessageIdResult() {}

    public StatusLocalMessageIdResult(long messageId, String localId, int status, String statusText) {
        this.messageId = messageId;
        this.localId = localId;
        this.status = status;
        this.statusText = statusText;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
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
}
