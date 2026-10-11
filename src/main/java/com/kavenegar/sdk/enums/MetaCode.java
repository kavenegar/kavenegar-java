package com.kavenegar.sdk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MetaCode {
    NOT_CHECKED(99),
    APPROVED(100),
    INVALID_API_KEY(101),
    EXPIRED_API_KEY(102),
    ACCOUNT_DISABLED(103),
    NOT_ENOUGH_CREDIT(104),
    SERVER_IS_BUSY(105),
    UNDEFINED_COMMAND(106),
    REQUEST_FAILED(107),
    PARAMETERS_BROKEN(108),
    INVALID_RECIPIENT(110),
    INVALID_SENDER_NUMBER(111),
    EMPTY_MESSAGE(112),
    RECIPIENT_TOO_LARGE(113),
    INVALID_DATE(114),
    MESSAGE_TOO_LARGE(115),
    RECIPIENT_COUNT_MISMATCH(116),
    UNKNOWN(-1),

    // .NET Aliases
    INVALID_RECP(110),
    InvalidRecp(110),
    RECP_IS_TOO_LARGE(113),
    RecpIsTooLarge(113),
    MSG_IS_TOO_LARGE(115),
    MsgIsTooLarge(115),
    RECP_NOT_EQUAL_WITH_MESSAGE(116),
    RecpNotEqualWithMessage(116);

    private final int getValue;

    MetaCode(int getValue) {
        this.getValue = getValue;
    }

    @JsonValue
    public int getValue() {
        return getValue;
    }

    public int getCode() {
        return getValue;
    }

    @JsonCreator
    public static MetaCode fromValue(int value) {
        for (MetaCode code : MetaCode.values()) {
            if (code.getValue == value) {
                return code;
            }
        }
        return UNKNOWN;
    }

    public static MetaCode valueOf(int value) {
        return fromValue(value);
    }
}
