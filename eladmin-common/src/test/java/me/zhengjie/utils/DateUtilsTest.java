package me.zhengjie.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

public class DateUtilsTest {
    @Test
    public void test1() {
        long l = System.currentTimeMillis() / 1000;
        LocalDateTime localDateTime = DateUtil.fromTimeStamp(l);
        System.out.print(DateUtil.localDateTimeFormatyMdHms(localDateTime));
    }

    @Test
    public void test2() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(DateUtil.localDateTimeFormatyMdHms(now));
        Date date = DateUtil.toDate(now);
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(date);
        System.out.println(DateUtil.localDateTimeFormatyMdHms(localDateTime));
        LocalDateTime localDateTime1 = DateUtil.fromTimeStamp(date.getTime() / 1000);
        System.out.println(DateUtil.localDateTimeFormatyMdHms(localDateTime1));
    }
}
