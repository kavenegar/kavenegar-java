/*
 * Represents account information including credit, expiration, and type.
 * Provides utility methods for validation, formatting, and comparison.
 */
package com.kavenegar.sdk.models;

import com.google.gson.JsonObject;
import java.util.Objects;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AccountInfoResult {

    private final Long remainCredit;
    private final Long expireDate;
    private final String type;

    public AccountInfoResult(JsonObject json) {
        this.remainCredit = json.has("remaincredit") && !json.get("remaincredit").isJsonNull()
                ? json.get("remaincredit").getAsLong() : 0L;
        this.expireDate = json.has("expiredate") && !json.get("expiredate").isJsonNull()
                ? json.get("expiredate").getAsLong() : 0L;
        this.type = json.has("type") && !json.get("type").isJsonNull()
                ? json.get("type").getAsString() : "Unknown";
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

    public boolean isExpired() {
        return System.currentTimeMillis() > expireDate;
    }

    public String getFormattedExpireDate() {
        return expireDate == 0L
                ? "N/A"
                : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(expireDate));
    }

    public boolean hasSufficientCredit(long threshold) {
        return remainCredit >= threshold;
    }

    public String toJsonString() {
        JsonObject json = new JsonObject();
        json.addProperty("remaincredit", remainCredit);
        json.addProperty("expiredate", expireDate);
        json.addProperty("type", type);
        return json.toString();
    }

    public String getAccountStatus() {
        if (isExpired()) return "Expired";
        if (!hasSufficientCredit(1)) return "Insufficient Credit";
        return "Active";
    }

    public String getCreditLevel() {
        if (remainCredit >= 10000) return "High";
        if (remainCredit >= 1000) return "Medium";
        return "Low";
    }

    public long getDaysUntilExpiration() {
        long now = System.currentTimeMillis();
        return expireDate > now
                ? TimeUnit.MILLISECONDS.toDays(expireDate - now)
                : 0L;
    }

    public boolean shouldWarnExpiration(int warningDays) {
        return !isExpired() && getDaysUntilExpiration() <= warningDays;
    }

    public boolean needsUpgrade() {
        return getCreditLevel().equals("Low") || shouldWarnExpiration(5);
    }

    public boolean isTrialAccount() {
        return "trial".equalsIgnoreCase(type);
    }

    public boolean isPremiumAccount() {
        return "premium".equalsIgnoreCase(type) || "enterprise".equalsIgnoreCase(type);
    }

    public String getShortSummary() {
        return String.format("Type: %s | Status: %s | Credit: %d | Expires: %s",
                type, getAccountStatus(), remainCredit, getFormattedExpireDate());
    }

    @Override
    public String toString() {
        return String.format("AccountInfoResult{remainCredit=%d, expireDate=%s, type='%s', status='%s', creditLevel='%s', daysUntilExpiration=%d}",
                remainCredit,
                getFormattedExpireDate(),
                type,
                getAccountStatus(),
                getCreditLevel(),
                getDaysUntilExpiration());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountInfoResult)) return false;
        AccountInfoResult that = (AccountInfoResult) o;
        return Objects.equals(remainCredit, that.remainCredit)
                && Objects.equals(expireDate, that.expireDate)
                && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remainCredit, expireDate, type);
    }
}
