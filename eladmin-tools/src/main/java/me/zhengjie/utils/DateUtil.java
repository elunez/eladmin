package me.zhengjie.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 黄星星
 * @date 2020-03-27
 */
public class DateUtil {
    public static String format_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";

    public static String parseToStr(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
