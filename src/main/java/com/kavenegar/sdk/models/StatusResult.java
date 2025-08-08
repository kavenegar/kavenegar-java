/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.sdk.models;

import com.google.gson.JsonObject;
import com.kavenegar.sdk.enums.MessageStatus;
import java.util.Objects;

/**
 * Represents the status result of a sent message.
 * Includes message ID, status enum, and a status text.
 * 
 * The code was originally written by Mohsen and later developed further by Reza.
 */
public class StatusResult {

    private int messageId;
    private MessageStatus status;
    private String statusText;

    protected StatusResult() {
        // For possible use by subclasses or deserialization.
    }

    public StatusResult(JsonObject json) {
        this.messageId = json.has("messageid") && !json.get("messageid").isJsonNull()
                ? json.get("messageid").getAsInt() : -1;
        this.status = MessageStatus.valueOf(json.get("status").getAsInt());
        this.statusText = json.has("statustext") && !json.get("statustext").isJsonNull()
                ? json.get("statustext").getAsString() : "";
    }

    public int getMessageId() {
        return messageId;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public String getStatusText() {
        return statusText;
    }

    /**
     * Checks if the status represents a successful delivery.
     */
    public boolean isSuccessful() {
        return status != null && status == MessageStatus.Delivered;
    }

    /**
     * Provides a concise summary of the message status.
     */
    public String getSummary() {
        return String.format("Message ID: %d | Status: %s (%s)",
                messageId, statusText, status);
    }

    @Override
    public String toString() {
        return "StatusResult{" +
                "messageId=" + messageId +
                ", status=" + status +
                ", statusText='" + statusText + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatusResult)) return false;
        StatusResult that = (StatusResult) o;
        return messageId == that.messageId &&
                status == that.status &&
                Objects.equals(statusText, that.statusText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, status, statusText);
    }
}
