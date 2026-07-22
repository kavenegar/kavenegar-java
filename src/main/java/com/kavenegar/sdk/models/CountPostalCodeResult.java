package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountPostalCodeResult {

    @JsonProperty("section")
    private String section;

    @JsonProperty("value")
    private long value;

    public CountPostalCodeResult() {}

    public CountPostalCodeResult(String section, long value) {
        this.section = section;
        this.value = value;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}