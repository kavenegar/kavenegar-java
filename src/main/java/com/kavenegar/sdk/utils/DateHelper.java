package com.kavenegar.sdk.utils;

import java.time.Instant;
import java.util.Date;

public final class DateHelper {

    private DateHelper() {
    }

    public static Date unixTimestampToDateTime(long unixTimeStamp) {
        return UnixTime.fromEpochSecondsToDate(unixTimeStamp);
    }

    public static long dateTimeToUnixTimestamp(Date dateTime) {
        return UnixTime.toEpochSeconds(dateTime);
    }

    public static long dateTimeToUnixTimestamp(Instant instant) {
        return UnixTime.toEpochSeconds(instant);
    }
}
