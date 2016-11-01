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
public class AccountInfoResult {

    long remaincredit;
    long expiredate;
    String type;

    public long getRemainCredit() {
        return remaincredit;
    }
    public void setRemainCredit(long RemainCredit) {
        this.remaincredit = RemainCredit;
    }
    public long getExpireDate() {
        return expiredate;
    }

    public String getType() {
        return type;
    }

   

    public void setExpireDate(long expiredate) {
        this.expiredate = expiredate;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
