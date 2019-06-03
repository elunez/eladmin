package me.zhengjie.utils;

import java.util.Collection;

/**
 * @描述 : 用于空对象处理
 * @作者 : Dong ZhaoYang
 * @日期 : 2017/01/19
 * @时间 : 09:52
 */
public class NullHelp {

    public static boolean isNull(Object obj) {
        return obj == null ||
                (obj instanceof String && isNullStr((String) obj)) ||
                (obj instanceof Collection && ((Collection) obj).isEmpty());
    }

    public static boolean isNullStr(String str) {
        str = str.trim();
        return str.isEmpty()/* || str.equalsIgnoreCase("null")
                || str.equalsIgnoreCase("undefined")*/;
    }

    public static void check(Object... obj) throws Exception {
        if (obj == null) {
            throw new RuntimeException("数组参数不能为空");
        }
        for (int i = 0; i < obj.length; i++) {
            Object o = obj[i];
            if (o == null) {
                throw new RuntimeException("obj[" + i + "]不能为空");
            }
        }
    }

}
