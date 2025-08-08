/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.sdk.enums;

/**
 * Enumeration representing metadata/result codes returned by the Kavenegar API.
 * <p>
 * This refactored version focuses on readability, maintainability and
 * safer usage across the codebase:
 * <ul>
 *   <li>Consistent enum identifiers (UPPER_SNAKE_CASE)</li>
 *   <li>Immutable integer values associated with each enum</li>
 *   <li>Clear JavaDoc on purpose and behaviour</li>
 *   <li>Utility lookup method {@link #fromValue(int)} for safe mapping</li>
 * </ul>
 *
 * The original authors: Mohsen (initial) and Reza (further development).
 */
public enum MetaData {

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
    RECIPIENT_LIST_TOO_LARGE(113),
    INVALID_DATE(114),
    MESSAGE_TOO_LARGE(115),
    RECIPIENT_COUNT_MISMATCH(116);

    private final int code;

    MetaData(int code) {
        this.code = code;
    }

    /**
     * Returns the numeric API code for this metadata value.
     *
     * @return API integer code
     */
    public int getValue() {
        return code;
    }

    /**
     * Safely maps an integer code to its corresponding MetaData enum.
     * Returns {@code null} if there is no matching enum — callers should
     * handle the null case explicitly.
     *
     * @param value integer value returned by the API
     * @return matching MetaData or {@code null} when unknown
     */
    public static MetaData fromValue(int value) {
        for (MetaData m : MetaData.values()) {
            if (m.code == value) return m;
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s(%d)", name(), code);
    }
}
