package com.kavenegar.sdk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum VerifyLookupType {
    SMS(0),
    CALL(1),

    // Aliases
    Sms(0),
    Call(1);

    private final int value;

    VerifyLookupType(int value) {
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
    public static VerifyLookupType fromValue(int value) {
        for (VerifyLookupType type : VerifyLookupType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return SMS;
    }

    public static VerifyLookupType valueOf(int value) {
        return fromValue(value);
    }
}
