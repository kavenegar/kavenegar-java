/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.sdk.models;

import com.google.gson.JsonObject;

/**
 * @author mohsen
 */
public class SendResult {

    private Long messageId;
    private String message;
    private Integer status;
    private String statusText;
    private String sender;
    private String receptor;
    private Long date;
    private Integer cost;


    public SendResult(JsonObject json) {
        this.cost = json.get("cost").getAsInt();
        this.date = json.get("date").getAsLong();
        this.messageId = json.get("messageid").getAsLong();
        this.message = json.get("message").getAsString();
        this.receptor = json.get("receptor").getAsString();
        this.status = json.get("status").getAsInt();
        this.statusText = json.get("statustext").getAsString();
        this.sender = json.get("sender").getAsString();
    }

    public long getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
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

    public long getDate() {
        return date;
    }

    public int getCost() {
        return cost;
    }
}