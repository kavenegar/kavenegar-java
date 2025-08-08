package com.kavenegar.sdk.enums;

/**
 * Enum representing various statuses a message can have.
 * The code was originally written by Mohsen and later developed further by Reza.
 */
public enum MessageStatus {
    Queued(1),
    Schulded(2),
    SentToCenter(4),
    Delivered(10),
    Undelivered(11),
    Canceled(13),
    Filtered(14),
    Received(50),
    Incorrect(100);

    private final int value;

    MessageStatus(int type) {
        this.value = type;
    }

    public int getValue() {
        return value;
    }

    public static MessageStatus valueOf(int type) {
        for (MessageStatus code : MessageStatus.values()) {
            if (type == code.getValue()) {
                return code;
            }
        }
        return null;
    }

    /**
     * Determines if the message is in a final (completed) state.
     *
     * @return true if the status is Delivered, Undelivered, Canceled, or Incorrect.
     */
    public boolean isFinalState() {
        return this == Delivered || this == Undelivered || this == Canceled || this == Incorrect;
    }

    /**
     * Provides a human-readable description of the message status.
     *
     * @return a string description of the status.
     */
    public String getDescription() {
        switch (this) {
            case Queued:
                return "Message is queued for sending.";
            case Schulded:
                return "Message is scheduled to be sent.";
            case SentToCenter:
                return "Message has been sent to the operator center.";
            case Delivered:
                return "Message has been successfully delivered.";
            case Undelivered:
                return "Message could not be delivered.";
            case Canceled:
                return "Message was canceled before being sent.";
            case Filtered:
                return "Message was filtered due to content restrictions.";
            case Received:
                return "Message was received by the server.";
            case Incorrect:
                return "Message contained incorrect data or destination.";
            default:
                return "Unknown message status.";
        }
    }
}
