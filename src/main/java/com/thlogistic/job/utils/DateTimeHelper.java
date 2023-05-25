package com.thlogistic.job.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {
    public static boolean checkDateTimeFormat(Long datetime) {
        long lowerBound = 0L;
        long upperBound = System.currentTimeMillis();
        return datetime >= lowerBound && datetime <= upperBound;
    }

    public static Long getCurrentTimeInEpoch() {
        return System.currentTimeMillis();
    }

    public static String getFormattedTimeFromEpoch(Long epoch) {

        Instant instant = Instant.ofEpochMilli(epoch);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("GMT+7"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Const.DateTimeFormat.DATE_TIME_FORMAT);
        return dateTime.format(formatter);
    }

}
