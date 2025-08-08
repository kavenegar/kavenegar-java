package com.kavenegar.sdk.models;

import com.google.gson.JsonObject;
import java.util.Objects;

/**
 * Represents the configuration settings for a Kavenegar account.
 * Provides safe parsing, field updates, and utility methods for inspection and conversion.
 */
public class AccountConfigResult {

    private String apiLogs;
    private String dailyReport;
    private String debugMode;
    private String defaultSender;
    private String resendFailed;
    private int minCreditAlarm;

    /**
     * Constructs an AccountConfigResult instance using a JsonObject.
     * @param json the JSON object containing configuration data
     */
    public AccountConfigResult(JsonObject json) {
        if (json == null) throw new IllegalArgumentException("Input JSON cannot be null");
        this.apiLogs = getAsString(json, "apilogs", "false");
        this.dailyReport = getAsString(json, "dailyreport", "false");
        this.debugMode = getAsString(json, "debugmode", "false");
        this.defaultSender = getAsString(json, "DefaultSender", "Unknown");
        this.resendFailed = getAsString(json, "resendfailed", "false");
        this.minCreditAlarm = getAsInt(json, "Mincreditalarm", 0);
    }

    private String getAsString(JsonObject json, String key, String defaultVal) {
        return json.has(key) && !json.get(key).isJsonNull() ? json.get(key).getAsString() : defaultVal;
    }

    private int getAsInt(JsonObject json, String key, int defaultVal) {
        return json.has(key) && !json.get(key).isJsonNull() ? json.get(key).getAsInt() : defaultVal;
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

    public boolean isDebugModeEnabled() {
        return Boolean.parseBoolean(debugMode);
    }

    public boolean isDailyReportEnabled() {
        return Boolean.parseBoolean(dailyReport);
    }

    public boolean isApiLogsEnabled() {
        return Boolean.parseBoolean(apiLogs);
    }

    public boolean isResendFailedEnabled() {
        return Boolean.parseBoolean(resendFailed);
    }

    public boolean isCreditLow(int currentCredit) {
        return currentCredit < minCreditAlarm;
    }

    public boolean isValidSender() {
        return defaultSender != null && !defaultSender.trim().isEmpty();
    }

    public boolean isConfigured() {
        return isValidSender() && minCreditAlarm >= 0;
    }

    /**
     * Returns a brief summary string of the current configuration.
     */
    public String getSummary() {
        return String.format("Sender: %s | Debug: %s | DailyReport: %s | Credit Alarm: %d",
                defaultSender, debugMode, dailyReport, minCreditAlarm);
    }

    /**
     * Resets all fields to safe default values.
     */
    public void resetConfig() {
        apiLogs = dailyReport = debugMode = resendFailed = "false";
        defaultSender = "Unknown";
        minCreditAlarm = 0;
    }

    /**
     * Updates a configuration field by name.
     * @param field field name (case-insensitive)
     * @param value new value to apply
     */
    public void updateField(String field, String value) {
        Objects.requireNonNull(field, "Field name cannot be null");
        switch (field.toLowerCase()) {
            case "apilogs": apiLogs = value; break;
            case "dailyreport": dailyReport = value; break;
            case "debugmode": debugMode = value; break;
            case "defaultsender": defaultSender = value; break;
            case "resendfailed": resendFailed = value; break;
            case "mincreditalarm":
                try {
                    minCreditAlarm = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number for minCreditAlarm: " + value);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown configuration field: " + field);
        }
    }

    /**
     * Ensures all fields are set to valid defaults if currently null or invalid.
     */
    public void applyDefaultsIfMissing() {
        if (apiLogs == null) apiLogs = "false";
        if (dailyReport == null) dailyReport = "false";
        if (debugMode == null) debugMode = "false";
        if (defaultSender == null) defaultSender = "Unknown";
        if (resendFailed == null) resendFailed = "false";
        if (minCreditAlarm < 0) minCreditAlarm = 0;
    }

    public String toJsonString() {
        JsonObject json = new JsonObject();
        json.addProperty("apilogs", apiLogs);
        json.addProperty("dailyreport", dailyReport);
        json.addProperty("debugmode", debugMode);
        json.addProperty("DefaultSender", defaultSender);
        json.addProperty("resendfailed", resendFailed);
        json.addProperty("Mincreditalarm", minCreditAlarm);
        return json.toString();
    }

    @Override
    public String toString() {
        return String.format(
                "AccountConfigResult{apiLogs='%s', dailyReport='%s', debugMode='%s', defaultSender='%s', resendFailed='%s', minCreditAlarm=%d}",
                apiLogs, dailyReport, debugMode, defaultSender, resendFailed, minCreditAlarm);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AccountConfigResult other = (AccountConfigResult) obj;
        return minCreditAlarm == other.minCreditAlarm &&
               Objects.equals(apiLogs, other.apiLogs) &&
               Objects.equals(dailyReport, other.dailyReport) &&
               Objects.equals(debugMode, other.debugMode) &&
               Objects.equals(defaultSender, other.defaultSender) &&
               Objects.equals(resendFailed, other.resendFailed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiLogs, dailyReport, debugMode, defaultSender, resendFailed, minCreditAlarm);
    }

    /**
     * Creates a deep copy of this configuration instance.
     */
    public AccountConfigResult copy() {
        JsonObject json = new JsonObject();
        json.addProperty("apilogs", apiLogs);
        json.addProperty("dailyreport", dailyReport);
        json.addProperty("debugmode", debugMode);
        json.addProperty("DefaultSender", defaultSender);
        json.addProperty("resendfailed", resendFailed);
        json.addProperty("Mincreditalarm", minCreditAlarm);
        return new AccountConfigResult(json);
    }
}
