package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kavenegar.sdk.enums.MediaReviewReason;
import com.kavenegar.sdk.enums.MediaReviewStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaReviewResult {

    @JsonProperty("status")
    private int status;

    @JsonProperty("status_desc")
    private String statusDescription;

    @JsonProperty("reason")
    private int reason;

    @JsonProperty("reason_desc")
    private String reasonDescription;

    public MediaReviewResult() {}

    public int getStatus() {
        return status;
    }

    public MediaReviewStatus getMediaReviewStatus() {
        return MediaReviewStatus.fromValue(status);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public int getReason() {
        return reason;
    }

    public MediaReviewReason getMediaReviewReason() {
        return MediaReviewReason.fromValue(reason);
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public String getReasonDescription() {
        return reasonDescription;
    }

    public void setReasonDescription(String reasonDescription) {
        this.reasonDescription = reasonDescription;
    }
}
