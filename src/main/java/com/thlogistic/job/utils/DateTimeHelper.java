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

    public static String getMonthOnlyFromEpoch(Long epoch) {
        Instant instant = Instant.ofEpochMilli(epoch);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("GMT+7"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Const.DateTimeFormat.MONTH_ONLY_FORMAT);
        return dateTime.format(formatter);
    }

    public static Long getStartOfYearTimestamp(Integer year) {
        LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0, 0);
        return startOfYear.atZone(ZoneId.of("GMT+7")).toInstant().toEpochMilli();
    }

    public static Long getEndOfYearTimestamp(Integer year) {
        LocalDateTime endOfYear = LocalDateTime.of(year, 12, 31, 23, 59, 59);
        return endOfYear.atZone(ZoneId.of("GMT+7")).toInstant().toEpochMilli();
    }

}
