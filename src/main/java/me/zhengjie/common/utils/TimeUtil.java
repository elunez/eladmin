package me.zhengjie.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author jie
 * @date 2018-12-13
 *
 * 日期工具
 */
public class TimeUtil {

    public static String getWeekDay(){
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekDays[w];
    }
}
