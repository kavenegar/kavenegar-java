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
public class ReciveResult {
    long messageid;
    String message;
    String sender;
    String receptor;
    long date;

    
    
    public void setMessageId(long messageid) {
        this.messageid = messageid;
    }
    public long getMessageId() {
        return messageid;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getSender() {
        return sender;
    }
    
    public void setDate(long date) {
        this.date = date;
    }
    public long getDate() {
        return date;
    }
    
    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }
    public String getReceptor() {
        return receptor;
    }
   
    
    
}
