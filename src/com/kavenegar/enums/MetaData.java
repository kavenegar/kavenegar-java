/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.enums;

import com.kavenegar.enums.*;

/**
 *
 * @author Mohsen
 */
public enum MetaData {

    NotChecked(99),
    Approved(100),
    InvalidApiKey(101),
    ExpiredApiKey(102),
    AccountDisabled(103),
    NotEnoughCredit(104),
    ServerisBusy(105),
    UndefinedCommand(106),
    RequestFailed(107),
    ParametersBroken(108),
    InvalidRecp(110),
    InvalidSenderNumber(111),
    EmptyMessage(112),
    RecpIsTooLarge(113),
    InvalidDate(114),
    MsgIsTooLarge(115),
    RecpNotEqualWithMessage(116);
    private int value;

    private MetaData(int type) {
        this.value = type;
    }

    public int getValue() {
        return value;
    }

    public static MetaData valueOf(int type) {
        for (MetaData code : MetaData.values()) {
            if (type == code.getValue()) {
                return code;
            }
        }
        return null;
    }
}
