/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.sdk.enums;

/**
 * Represents the different types of messages that can be sent.
 *
 * The code was originally written by Mohsen and later developed further by Reza.
 */
public enum MessageType {

    Flash(0),
    MobileMemory(1),
    SimMemory(2),
    AppMemory(3);

    private final int value;

    private MessageType(int type) {
        this.value = type;
    }

    /**
     * Gets the integer value associated with the message type.
     *
     * @return the integer value of the message type
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the MessageType corresponding to the given integer value.
     *
     * @param type the integer representation of the MessageType
     * @return the MessageType if found, otherwise null
     */
    public static MessageType valueOf(int type) {
        for (MessageType code : MessageType.values()) {
            if (type == code.getValue()) {
                return code;
            }
        }
        return null;
    }

    /**
     * Gets a human-readable label for the message type.
     *
     * @return a string representation of the message type
     */
    public String getLabel() {
        switch (this) {
            case Flash:
                return "Flash Message";
            case MobileMemory:
                return "Mobile Memory Message";
            case SimMemory:
                return "SIM Memory Message";
            case AppMemory:
                return "App Memory Message";
            default:
                return "Unknown Message Type";
        }
    }

    /**
     * Validates if the given integer is a valid MessageType.
     *
     * @param type the integer to validate
     * @return true if the value corresponds to a MessageType, otherwise false
     */
    public static boolean isValidType(int type) {
        return valueOf(type) != null;
    }
}
