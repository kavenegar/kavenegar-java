package com.kavenegar.sdk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MediaReviewStatus {
    APPROVED(1),
    REJECTED(2);

    private final int value;

    MediaReviewStatus(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public int getCode() {
        return value;
    }

    @JsonCreator
    public static MediaReviewStatus fromValue(int value) {
        for (MediaReviewStatus status : MediaReviewStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        return REJECTED;
    }

    public static MediaReviewStatus valueOf(int value) {
        return fromValue(value);
    }
}
