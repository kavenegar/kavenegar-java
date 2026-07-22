package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountOutboxResult {

    @JsonProperty("sumpart")
    private long sumPart;

    @JsonProperty("cost")
    private long cost;

    public CountOutboxResult() {}

    public CountOutboxResult(long sumPart, long cost) {
        this.sumPart = sumPart;
        this.cost = cost;
    }

    public long getSumPart() {
        return sumPart;
    }

    public void setSumPart(long sumPart) {
        this.sumPart = sumPart;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }
}