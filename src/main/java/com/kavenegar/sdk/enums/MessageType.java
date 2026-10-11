package com.kavenegar.sdk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageType {
    FLASH(0),
    MOBILE_MEMORY(1),
    SIM_MEMORY(2),
    APP_MEMORY(3),

    // Legacy Aliases
    Flash(0),
    MobileMemory(1),
    SimMemory(2),
    AppMemory(3);

    private final int value;

    MessageType(int value) {
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
    public static MessageType fromValue(int value) {
        for (MessageType type : MessageType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return MOBILE_MEMORY;
    }

    public static MessageType valueOf(int value) {
        return fromValue(value);
    }
}
