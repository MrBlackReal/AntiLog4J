package dev.mrblackreal.antilog4j.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHelper {

    public static String getDateAndTime(long ms) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");
        Date result = new Date(ms);
        return simpleDateFormat.format(result);
    }
}