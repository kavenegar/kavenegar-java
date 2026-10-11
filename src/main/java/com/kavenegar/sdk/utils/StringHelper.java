package com.kavenegar.sdk.utils;

import java.util.Arrays;
import java.util.Collection;

public final class StringHelper {

    private StringHelper() {
    }

    public static String join(String delimiter, String[] items) {
        if (items == null || items.length == 0) {
            return "";
        }
        return String.join(delimiter, items);
    }

    public static String join(String delimiter, long[] items) {
        if (items == null || items.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            if (i > 0) {
                sb.append(delimiter);
            }
            sb.append(items[i]);
        }
        return sb.toString();
    }

    public static String join(String delimiter, Collection<?> items) {
        if (items == null || items.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Object item : items) {
            if (!first) {
                sb.append(delimiter);
            }
            sb.append(item);
            first = false;
        }
        return sb.toString();
    }
}
