package com.kavenegar.sdk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MediaReviewReason {
    UNKNOWN(0),
    INAPPROPRIATE_CONTENT(1),
    NUDITY_OR_SEXUAL_CONTENT(2),
    VIOLENCE_OR_GRAPHIC_CONTENT(3),
    HATE_OR_HARASSMENT(4),
    ILLEGAL_CONTENT(5),
    SELF_HARM_OR_SUICIDE(6),
    SPAM_OR_SCAM(7),
    MISLEADING_OR_FAKE_CONTENT(8),
    IMPERSONATION(9),
    COPYRIGHT_VIOLATION(10),
    TRADEMARK_VIOLATION(11),
    PRIVACY_VIOLATION(12),
    LOW_QUALITY(13),
    CORRUPTED_FILE(14),
    UNSUPPORTED_FORMAT(15),
    TOO_LARGE(16),
    EMPTY_OR_BLANK_MEDIA(17),
    IRRELEVANT_CONTENT(18),
    DUPLICATE_MEDIA(19),
    MISSING_REQUIRED_INFO(20),
    POLICY_VIOLATION(21),
    MANUAL_REVIEW_FAILED(22);

    private final int value;

    MediaReviewReason(int value) {
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
    public static MediaReviewReason fromValue(int value) {
        for (MediaReviewReason reason : MediaReviewReason.values()) {
            if (reason.value == value) {
                return reason;
            }
        }
        return UNKNOWN;
    }

    public static MediaReviewReason valueOf(int value) {
        return fromValue(value);
    }
}
