/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.sdk.models;

import com.google.gson.JsonObject;

/**
 * @author Kave
 */
public class AccountConfigResult {


    private String apiLogs;
    private String dailyReport;
    private String debugMode;
    private String defaultSender;
    private String resendFailed;
    private int minCreditAlarm;

    public AccountConfigResult(JsonObject json) {
        this.apiLogs = json.get("apilogs").getAsString();
        this.dailyReport = json.get("dailyreport").getAsString();
        this.debugMode = json.get("debugmode").getAsString();
        this.defaultSender = json.get("DefaultSender").getAsString();
        this.resendFailed = json.get("resendfailed").getAsString();
        this.minCreditAlarm = json.get("Mincreditalarm").getAsInt();
    }

    public String getApiLogs() {
        return apiLogs;
    }

    public String getDailyReport() {
        return dailyReport;
    }

    public String getDebugMode() {
        return debugMode;
    }

    public String getDefaultSender() {
        return defaultSender;
    }

    public String getResendFailed() {
        return resendFailed;
    }

    public int getMinCreditAlarm() {
        return minCreditAlarm;
    }
}
