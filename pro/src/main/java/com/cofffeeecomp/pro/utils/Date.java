package com.cofffeeecomp.pro.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Date {
    public static String getDateString(){
        var now = LocalDateTime.now();
        var formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmm");
        return now.format(formatter) + Random.randomAlphanumeric(10);
    }
}
