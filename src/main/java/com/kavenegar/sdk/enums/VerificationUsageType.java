package com.kavenegar.sdk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum VerificationUsageType {
    WEB_APPLICATION(0),
    MOBILE_APPLICATION(1),
    OTHER(2),
    DEV_TEST(3);

    private final int value;

    VerificationUsageType(int value) {
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
    public static VerificationUsageType fromValue(int value) {
        for (VerificationUsageType type : VerificationUsageType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return OTHER;
    }

    public static VerificationUsageType valueOf(int value) {
        return fromValue(value);
    }
}
