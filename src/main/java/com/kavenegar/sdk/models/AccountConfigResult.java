package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountConfigResult {

    @JsonProperty("apilogs")
    private String apiLogs;

    @JsonProperty("dailyreport")
    private String dailyReport;

    @JsonProperty("debugmode")
    private String debugMode;

    @JsonProperty("defaultsender")
    private String defaultSender;

    @JsonProperty("mincreditalarm")
    private String minCreditAlarm;

    @JsonProperty("resendfailed")
    private String resendFailed;

    public AccountConfigResult() {}

    public String getApiLogs() {
        return apiLogs;
    }

    public void setApiLogs(String apiLogs) {
        this.apiLogs = apiLogs;
    }

    public String getDailyReport() {
        return dailyReport;
    }

    public void setDailyReport(String dailyReport) {
        this.dailyReport = dailyReport;
    }

    public String getDebugMode() {
        return debugMode;
    }

    public void setDebugMode(String debugMode) {
        this.debugMode = debugMode;
    }

    public String getDefaultSender() {
        return defaultSender;
    }

    public void setDefaultSender(String defaultSender) {
        this.defaultSender = defaultSender;
    }

    public String getMinCreditAlarm() {
        return minCreditAlarm;
    }

    public void setMinCreditAlarm(String minCreditAlarm) {
        this.minCreditAlarm = minCreditAlarm;
    }

    public String getResendFailed() {
        return resendFailed;
    }

    public void setResendFailed(String resendFailed) {
        this.resendFailed = resendFailed;
    }
}
