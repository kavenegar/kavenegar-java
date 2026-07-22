package com.kavenegar.sdk.utils;

import java.time.Instant;
import java.util.Date;

public final class UnixTime {

    private UnixTime() {}

    public static long toEpochSeconds(Instant instant) {
        return instant != null ? instant.getEpochSecond() : 0L;
    }

    public static Instant fromEpochSeconds(long seconds) {
        return seconds > 0 ? Instant.ofEpochSecond(seconds) : null;
    }

    public static long toEpochSeconds(Date date) {
        return date != null ? date.getTime() / 1000L : 0L;
    }

    public static Date fromEpochSecondsToDate(long seconds) {
        return seconds > 0 ? new Date(seconds * 1000L) : null;
    }
}
