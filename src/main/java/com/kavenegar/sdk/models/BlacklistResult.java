package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BlacklistResult {

    @JsonProperty("linenumber")
    private String lineNumber;

    @JsonProperty("receptor")
    private String receptor;

    @JsonProperty("status")
    private String status;

    public BlacklistResult() {}

    public BlacklistResult(String lineNumber, String receptor, String status) {
        this.lineNumber = lineNumber;
        this.receptor = receptor;
        this.status = status;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
