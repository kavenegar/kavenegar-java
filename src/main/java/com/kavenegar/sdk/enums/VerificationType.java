package com.kavenegar.sdk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum VerificationType {
    DEFAULT(0),
    MESSAGE(1),
    CALL(2),
    DISABLED(3);

    private final int value;

    VerificationType(int value) {
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
    public static VerificationType fromValue(int value) {
        for (VerificationType type : VerificationType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return DEFAULT;
    }

    public static VerificationType valueOf(int value) {
        return fromValue(value);
    }
}
