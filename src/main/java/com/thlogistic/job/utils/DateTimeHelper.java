package com.thlogistic.job.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {
    public static boolean checkDateTimeFormat(String datetime) {
        SimpleDateFormat formatter = new SimpleDateFormat(Const.dateTimeFormat);
        formatter.setLenient(false);
        try {
            formatter.parse(datetime);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String getCurrentTimeFormatted() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Const.dateTimeFormat);
        return now.format(formatter);
    }
}
