package com.ujo.gigiScheduler.common.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DateUtils {

    public static String addDate(String pattern, int days) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, days);

        return format.format(calendar.getTime());
    }

    public static LocalDateTime parse(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTime, formatter);

    }
}
