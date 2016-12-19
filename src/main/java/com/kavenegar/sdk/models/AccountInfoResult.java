/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.sdk.models;

import com.google.gson.JsonObject;

/**
 * @author mohsen
 */
public class AccountInfoResult {

    private  Long remainCredit;
    private Long expireDate;
    private String type;

    public AccountInfoResult(JsonObject json){
        this.remainCredit = json.get("remaincredit").getAsLong();
        this.expireDate = json.get("expiredate").getAsLong();
        this.type = json.get("type").getAsString();
    }

    public Long getRemainCredit() {
        return remainCredit;
    }

    public Long getExpireDate() {
        return expireDate;
    }

    public String getType() {
        return type;
    }
}
