/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.sdk.enums;

/**
 *
 * @author mohsen
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

    private MessageStatus(int type) {
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
}
