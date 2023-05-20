package com.thlogistic.job.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {
    public static boolean checkDateTimeFormat(String datetime) {
        SimpleDateFormat formatter = new SimpleDateFormat(Const.DateTimeFormat.DATE_TIME_FORMAT);
        formatter.setLenient(false);
        try {
            formatter.parse(datetime);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String getCurrentTimeFormatted() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("GMT+7"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Const.DateTimeFormat.DATE_TIME_FORMAT);
        return now.format(formatter);
    }
}
