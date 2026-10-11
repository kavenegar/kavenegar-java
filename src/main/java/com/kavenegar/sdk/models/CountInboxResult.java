package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountInboxResult {

    @JsonProperty("startdate")
    private long startDate;

    @JsonProperty("enddate")
    private long endDate;

    @JsonProperty("sumcount")
    private long sumCount;

    public CountInboxResult() {}

    public CountInboxResult(long startDate, long endDate, long sumCount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.sumCount = sumCount;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public long getSumCount() {
        return sumCount;
    }

    public void setSumCount(long sumCount) {
        this.sumCount = sumCount;
    }
}