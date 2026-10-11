package com.kavenegar.sdk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageStatus {
    QUEUED(1),
    SCHEDULED(2),
    SENT_TO_CENTER(4),
    SENT_TO_CARRIER(5),
    DELIVERED(10),
    UNDELIVERED(11),
    CANCELED(13),
    FILTERED(14),
    RECEIVED(50),
    INCORRECT(100),
    UNKNOWN(-1),

    // Legacy & .NET Aliases
    Queued(1),
    Scheduled(2),
    Schulded(2),
    SentToCenter(4),
    SentToCarrier(5),
    SentToBlackList(5),
    Delivered(10),
    Undelivered(11),
    Canceled(13),
    Filtered(14),
    Received(50),
    Incorrect(100);

    private final int value;

    MessageStatus(int value) {
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
    public static MessageStatus fromValue(int value) {
        for (MessageStatus status : MessageStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        return UNKNOWN;
    }

    public static MessageStatus valueOf(int value) {
        return fromValue(value);
    }
}
