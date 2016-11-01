/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.models;

import com.kavenegar.models.*;
import com.kavenegar.enums.MessageStatus;

/**
 *
 * @author mohsen
 */
public class StatusResult {
    int messageid;
    MessageStatus status;
    String statustext;

    public int getMessageId() {
        return messageid;
    }

    public void setMessageId(int messageid) {
        this.messageid = messageid;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public String getStatusText() {
        return statustext;
    }

    public void setStatusText(String statustext) {
        this.statustext = statustext;
    }
    
    
}
