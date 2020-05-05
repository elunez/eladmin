package me.zhengjie.utils;

import java.util.Date;

/**
 * @author 黄星星
 * @date 2020-03-27
 */
public class PiciUtil {

    public static String initPici(String code){
        return code + "_" + DateUtil.parseToStr(new Date(), DateUtil.format_yyyyMMddHHmmss);
    }
}
