/*
 * Represents the total count of inbox messages within a specified date range.
 * Provides utility methods for formatting, reporting, and data validation.
 */
package com.kavenegar.sdk.models;

import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CountInboxResult {

    private final Long startDate;
    private final Long endDate;
    private final Long sumCount;

    public CountInboxResult(JsonObject json) {
        this.startDate = json.has("startdate") && !json.get("startdate").isJsonNull()
                ? json.get("startdate").getAsLong() : 0L;
        this.endDate = json.has("enddate") && !json.get("enddate").isJsonNull()
                ? json.get("enddate").getAsLong() : 0L;
        this.sumCount = json.has("sumcount") && !json.get("sumcount").isJsonNull()
                ? json.get("sumcount").getAsLong() : 0L;
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

    public String getFormattedStartDate() {
        return formatTimestamp(startDate);
    }

    public String getFormattedEndDate() {
        return formatTimestamp(endDate);
    }

    private String formatTimestamp(Long timestamp) {
        if (timestamp == null || timestamp <= 0) return "N/A";
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
    }

    public boolean isEmpty() {
        return sumCount == null || sumCount == 0;
    }

    public boolean isDateRangeValid() {
        return startDate != null && endDate != null && startDate > 0 && endDate > 0 && endDate >= startDate;
    }

    public double getMessageRatePerDay() {
        if (!isDateRangeValid()) return 0.0;
        long rangeInMillis = endDate - startDate;
        double days = rangeInMillis / (1000.0 * 60 * 60 * 24);
        return days > 0 ? sumCount / days : 0.0;
    }

    public String toJsonString() {
        JsonObject json = new JsonObject();
        json.addProperty("startdate", startDate);
        json.addProperty("enddate", endDate);
        json.addProperty("sumcount", sumCount);
        return json.toString();
    }

    public boolean isWithinRange(long timestamp) {
        return isDateRangeValid() && timestamp >= startDate && timestamp <= endDate;
    }

    public boolean isAboveThreshold(long threshold) {
        return sumCount != null && sumCount >= threshold;
    }

    public String getSummaryReport() {
        return String.format(
            "Inbox Summary:\n  Start: %s\n  End: %s\n  Messages: %d\n  Rate: %.2f/day",
            getFormattedStartDate(),
            getFormattedEndDate(),
            sumCount,
            getMessageRatePerDay()
        );
    }

    @Override
    public String toString() {
        return getSummaryReport();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountInboxResult)) return false;
        CountInboxResult that = (CountInboxResult) o;
        return Objects.equals(startDate, that.startDate) &&
               Objects.equals(endDate, that.endDate) &&
               Objects.equals(sumCount, that.sumCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, sumCount);
    }
}
