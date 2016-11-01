/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.models;

import com.kavenegar.models.*;

/**
 *
 * @author mohsen
 */
public class SendResult {

    long messageid;
    String message;
    int status;
    String statustext;
    String sender;
    String receptor;
    long date;
    int cost;

    public long getMessageId() {
       return messageid;
    }
    public void setMessageId(long messageid) {
        this.messageid = messageid;
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
    public void setStatus(int status) {
        this.status = status;
    }
       
    public String getStatusText() {
        return statustext;
    }
    public void setStatusText(String statustext) {
        this.statustext = statustext;
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