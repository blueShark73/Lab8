package com.itmo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * ох уж эти даты
 */
public class DateTimeAdapter {
    private static final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static ZonedDateTime parseToZonedDateTime(java.sql.Date date){
        return date.toLocalDate().atStartOfDay(ZoneId.systemDefault());
    }

    public static String parseToString(ZonedDateTime zonedDateTime){
        return dateFormat.format(Date.from(zonedDateTime.toInstant()));
    }
}
