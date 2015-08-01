package com.jeegoo.o2o.tehuiduo.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xintong on 2015/7/20.
 */
public class DateUtils {
    public static String format(Date date, String pattern) {
        if(date==null) return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);

    }
}
