/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.sdk.models;

import com.google.gson.JsonObject;
import java.util.Objects;

/**
 * Represents a single received message result from the API.
 * Encapsulates metadata about the message including sender, receptor,
 * message content, and timestamp.
 *
 * Provides basic validation, content checks, and identity comparison methods.
 *
 */
public class ReceiveResult {

    private final Long messageId;
    private final String message;
    private final String sender;
    private final String receptor;
    private final Long date;

    public ReceiveResult(JsonObject json) {
        this.messageId = json.has("messageid") && !json.get("messageid").isJsonNull()
                ? json.get("messageid").getAsLong() : null;
        this.date = json.has("date") && !json.get("date").isJsonNull()
                ? json.get("date").getAsLong() : null;
        this.message = json.has("message") && !json.get("message").isJsonNull()
                ? json.get("message").getAsString() : "";
        this.sender = json.has("sender") && !json.get("sender").isJsonNull()
                ? json.get("sender").getAsString() : "";
        this.receptor = json.has("receptor") && !json.get("receptor").isJsonNull()
                ? json.get("receptor").getAsString() : "";
    }

    public Long getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
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

    /**
     * Returns true if the message content is empty or null.
     */
    public boolean isMessageEmpty() {
        return message == null || message.trim().isEmpty();
    }

    /**
     * Returns true if the message contains the given keyword (case-insensitive).
     */
    public boolean containsKeyword(String keyword) {
        return message != null && keyword != null && message.toLowerCase().contains(keyword.toLowerCase());
    }

    /**
     * Returns a compact string representation of the message metadata.
     */
    public String getSummary() {
        return String.format("From: %s, To: %s, At: %d, Message: %s",
                sender, receptor, date, message);
    }

    @Override
    public String toString() {
        return "ReceiveResult{" +
                "messageId=" + messageId +
                ", message='" + message + '\'' +
                ", sender='" + sender + '\'' +
                ", receptor='" + receptor + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReceiveResult)) return false;
        ReceiveResult that = (ReceiveResult) o;
        return Objects.equals(messageId, that.messageId) &&
                Objects.equals(message, that.message) &&
                Objects.equals(sender, that.sender) &&
                Objects.equals(receptor, that.receptor) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, message, sender, receptor, date);
    }
}
