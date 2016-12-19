/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.sdk.models;

import com.google.gson.JsonObject;

/**
 * @author mohsen
 */
public class CountInboxResult {

    private Long startDate;
    private Long endDate;
    private  Long sumCount;

    public CountInboxResult(JsonObject json) {
        this.startDate = json.get("startdate").getAsLong();
        this.endDate = json.get("enddate").getAsLong();
        this.sumCount = json.get("sumcount").getAsLong();
    }

    public Long getStartDate() {
        return startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public Long getSumCount() {
        return sumCount;
    }
}