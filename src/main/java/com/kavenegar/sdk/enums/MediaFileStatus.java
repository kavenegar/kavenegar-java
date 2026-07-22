package com.kavenegar.sdk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MediaFileStatus {
    PENDING(0),
    APPROVED(1),
    REJECTED(2);

    private final int value;

    MediaFileStatus(int value) {
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
    public static MediaFileStatus fromValue(int value) {
        for (MediaFileStatus status : MediaFileStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        return PENDING;
    }

    public static MediaFileStatus valueOf(int value) {
        return fromValue(value);
    }
}
