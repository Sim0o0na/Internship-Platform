package org.isp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parseDate(String date) throws ParseException {
        Date resultDate;
        try {
            resultDate = sdf.parse(date);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Please enter valid date");
        }
        return resultDate;
    }

    public static String getDateAsText(Date date) throws ParseException {
        return sdf.format(date);
    }
}
