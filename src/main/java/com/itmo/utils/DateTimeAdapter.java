package com.itmo.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * ох уж эти даты
 */
public class DateTimeAdapter {
    public static final DateFormat defaultDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static ZonedDateTime parseToZonedDateTime(Timestamp timestamp){
        return timestamp.toLocalDateTime().atZone(ZoneId.systemDefault());
    }

    public static Timestamp parseToTimesTamp(ZonedDateTime zonedDateTime){
        return Timestamp.from(zonedDateTime.toInstant());
    }

    public static String parseToString(Date date){
        return defaultDateFormat.format(date);
    }

    public static String parseToString(Date date, DateFormat dateFormat){
        return dateFormat.format(date);
    }


    public static String parseToString(ZonedDateTime zonedDateTime){
        return defaultDateFormat.format(Date.from(zonedDateTime.toInstant()));
    }

    public static String parseToString(ZonedDateTime zonedDateTime, DateFormat dateFormat){
        return dateFormat.format(Date.from(zonedDateTime.toInstant()));

    }

    public static ZonedDateTime parseToZonedDateTime(String string) throws ParseException {
        Date date = defaultDateFormat.parse(string);
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }


    public static ZonedDateTime parseToZonedDateTime(String string, DateFormat format) throws ParseException {
        Date date = format.parse(string);
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
