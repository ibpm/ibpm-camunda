package com.github.ibpm.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * date util
 */
public class DateUtil {

    public static final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";

    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss SSS";

    public static synchronized Date parse(String dateStr, String format) {
        if (dateStr == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            date = null;
        }
        return date;
    }


    public static synchronized String format(Date date, String format) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }
}
