package com.shared.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String convertLocalDateTimeToString(LocalDateTime localDateTime) {
        // Custom format if needed
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Format LocalDateTime
        return localDateTime.format(formatter);
    }

}