package com.kavenegar.sdk.utils;


import java.util.List;

/**
 * Created by mohsenk on 2/23/17.
 */
public class StringUtils {

    public static String join(CharSequence delimiter, List<?> elements) {
        if (elements == null || elements.isEmpty()) {
            return "";
        }
        // Number of elements not likely worth Arrays.stream overhead.
        String result = elements.get(0).toString();
        for (int i = 1; i < elements.size(); i++) {
            result += delimiter + elements.get(i).toString();
        }
        return result;
    }

}
