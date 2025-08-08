/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.sdk.models;

import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Represents the result of an inbox message count operation, storing the total count
 * of received messages within a specified time range.
 * Provides utility methods for date formatting, message rate calculation,
 * and structured reporting.
 *
 * This version emphasizes clean code practices, null-safety, encapsulation,
 * and proper object comparison.
 *
 * The code was orginally written by Mohssen and later developed further by Reza.
 */
public class CountInboxResult {

    private final long startDate;
    private final long endDate;
    private final long sumCount;

    public CountInboxResult(JsonObject json) {
        this.startDate = extractLong(json, "startdate");
        this.endDate = extractLong(json, "enddate");
        this.sumCount = extractLong(json, "sumcount");
    }

    private long extractLong(JsonObject json, String key) {
        return json.has(key) && !json.get(key).isJsonNull() ? json.get(key).getAsLong() : 0L;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public long getSumCount() {
        return sumCount;
    }

    public long getDurationMillis() {
        return Math.max(endDate - startDate, 0);
    }

    public double getMessagesPerSecond() {
        long duration = getDurationMillis();
        return duration > 0 ? sumCount / (duration / 1000.0) : 0.0;
    }

    public double getMessagesPerDay() {
        double durationDays = getDurationMillis() / (1000.0 * 60 * 60 * 24);
        return durationDays > 0 ? sumCount / durationDays : 0.0;
    }

    public boolean isEmpty() {
        return sumCount <= 0;
    }

    public boolean isDateRangeValid() {
        return startDate > 0 && endDate > startDate;
    }

    public String getFormattedDate(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
    }

    public String getFormattedDateRange() {
        return getFormattedDate(startDate) + " to " + getFormattedDate(endDate);
    }

    public String getInboxSummary() {
        return String.format(
            "Inbox Summary: Messages=%d, Duration=%d ms (%.2f days), AvgRate=%.2f msg/sec",
            sumCount, getDurationMillis(), getDurationMillis() / (1000.0 * 60 * 60 * 24), getMessagesPerSecond()
        );
    }

    @Override
    public String toString() {
        return String.format(
            "CountInboxResult{startDate=%d, endDate=%d, sumCount=%d}",
            startDate, endDate, sumCount
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountInboxResult)) return false;
        CountInboxResult that = (CountInboxResult) o;
        return startDate == that.startDate && endDate == that.endDate && sumCount == that.sumCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, sumCount);
    }
}
