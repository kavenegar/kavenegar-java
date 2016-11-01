/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.enums;

import com.kavenegar.enums.*;

/**
 *
 * @author mohsen
 */
public enum MessageType {

    Flash(0),
    MobileMemory(1),
    SimMemory(2),
    AppMemory(3);
    private int value;

    private MessageType(int type) {
        this.value = type;
    }

    public int getValue() {
        return value;
    }

    public static MessageType valueOf(int type) {
        for (MessageType code : MessageType.values()) {
            if (type == code.getValue()) {
                return code;
            }
        }
        return null;
    }
}
