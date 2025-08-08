package com.kavenegar.sdk.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * The code was originally written by Mohsen and later developed further by Reza.
 */
public class StringUtils {

    public static String join(CharSequence delimiter, List<?> elements) {
        if (elements == null || elements.isEmpty()) {
            return "";
        }
        StringJoiner joiner = new StringJoiner(delimiter);
        for (Object element : elements) {
            joiner.add(element == null ? "null" : element.toString());
        }
        return joiner.toString();
    }

    // New function: Split a string into a list by delimiter
    public static List<String> split(String input, String delimiter) {
        List<String> result = new ArrayList<>();
        if (input == null || delimiter == null) {
            return result;
        }
        String[] parts = input.split(delimiter);
        for (String part : parts) {
            result.add(part);
        }
        return result;
    }

    // New function: Check if a string is null or empty
    public static boolean isNullOrEmpty(String input) {
        return input == null || input.isEmpty();
    }

    // New function: Repeat a string multiple times
    public static String repeat(String str, int count) {
        if (str == null || count <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(str.length() * count);
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    // New function: Capitalize the first letter of a string
    public static String capitalizeFirstLetter(String input) {
        if (isNullOrEmpty(input)) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    // New function: Reverse a string
    public static String reverse(String input) {
        if (input == null) {
            return null;
        }
        return new StringBuilder(input).reverse().toString();
    }
}
