/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.sdk.models;

import com.google.gson.JsonObject;
import java.util.Objects;

/**
 * The code was originally written by Mohsen and later developed further by Reza.
 * Represents the result of a send message operation, encapsulating metadata such as ID, message content,
 * status, sender and recipient info, timestamp, and cost.
 */
public class SendResult {

    private final Long messageId;
    private final String message;
    private final Integer status;
    private final String statusText;
    private final String sender;
    private final String receptor;
    private final Long date;
    private final Integer cost;

    public SendResult(JsonObject json) {
        this.cost = json.has("cost") && !json.get("cost").isJsonNull() ? json.get("cost").getAsInt() : 0;
        this.date = json.has("date") && !json.get("date").isJsonNull() ? json.get("date").getAsLong() : 0L;
        this.messageId = json.has("messageid") && !json.get("messageid").isJsonNull() ? json.get("messageid").getAsLong() : 0L;
        this.message = json.has("message") && !json.get("message").isJsonNull() ? json.get("message").getAsString() : "";
        this.receptor = json.has("receptor") && !json.get("receptor").isJsonNull() ? json.get("receptor").getAsString() : "";
        this.status = json.has("status") && !json.get("status").isJsonNull() ? json.get("status").getAsInt() : -1;
        this.statusText = json.has("statustext") && !json.get("statustext").isJsonNull() ? json.get("statustext").getAsString() : "";
        this.sender = json.has("sender") && !json.get("sender").isJsonNull() ? json.get("sender").getAsString() : "";
    }

    public Long getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getSender() {
        return sender;
    }

    public String getReceptor() {
        return receptor;
    }

    public Long getDate() {
        return date;
    }

    public Integer getCost() {
        return cost;
    }

    /**
     * Checks if the status code indicates a successful operation.
     */
    public boolean isSuccess() {
        return status != null && status == 1;
    }

    /**
     * Generates a human-readable summary of the result.
     */
    public String getSummary() {
        return String.format("MessageID: %d | Status: %s | To: %s | Cost: %d", messageId, statusText, receptor, cost);
    }

    @Override
    public String toString() {
        return "SendResult{" +
                "messageId=" + messageId +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", statusText='" + statusText + '\'' +
                ", sender='" + sender + '\'' +
                ", receptor='" + receptor + '\'' +
                ", date=" + date +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SendResult)) return false;
        SendResult that = (SendResult) o;
        return Objects.equals(messageId, that.messageId) &&
               Objects.equals(message, that.message) &&
               Objects.equals(status, that.status) &&
               Objects.equals(statusText, that.statusText) &&
               Objects.equals(sender, that.sender) &&
               Objects.equals(receptor, that.receptor) &&
               Objects.equals(date, that.date) &&
               Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, message, status, statusText, sender, receptor, date, cost);
    }
}
