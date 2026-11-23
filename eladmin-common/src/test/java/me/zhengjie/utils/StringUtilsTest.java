package me.zhengjie.utils;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static me.zhengjie.utils.StringUtils.getIp;
import static me.zhengjie.utils.StringUtils.getWeekDay;
import static me.zhengjie.utils.StringUtils.toCamelCase;
import static me.zhengjie.utils.StringUtils.toCapitalizeCamelCase;
import static me.zhengjie.utils.StringUtils.toUnderScoreCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StringUtilsTest {

    @Test
    public void testToCamelCase() {
        assertNull(toCamelCase(null));
    }

    @Test
    public void testToCapitalizeCamelCase() {
        assertNull(StringUtils.toCapitalizeCamelCase(null));
        assertEquals("HelloWorld", toCapitalizeCamelCase("hello_world"));
    }

    @Test
    public void testToUnderScoreCase() {
        assertNull(StringUtils.toUnderScoreCase(null));
        assertEquals("hello_world", toUnderScoreCase("helloWorld"));
        assertEquals("\u0000\u0000", toUnderScoreCase("\u0000\u0000"));
        assertEquals("\u0000_a", toUnderScoreCase("\u0000A"));
    }

    @Test
    public void testGetWeekDay() {
        // getWeekDay() 固定返回英文缩写，用默认 locale 断言会在中文环境得到“星期一”
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E", Locale.ENGLISH);
        assertEquals(simpleDateformat.format(new Date()), getWeekDay());
    }

    @Test
    public void testGetIPFromForwardedHeader() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("x-forwarded-for", "1.2.3.4, 5.6.7.8");
        // 多级代理时只取第一个地址
        assertEquals("1.2.3.4", getIp(request));
    }

    @Test
    public void testGetIPFallbackToRemoteAddr() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("x-forwarded-for", "unknown");
        request.addHeader("Proxy-Client-IP", "unknown");
        request.addHeader("WL-Proxy-Client-IP", "unknown");
        request.setRemoteAddr("10.0.0.5");
        assertEquals("10.0.0.5", getIp(request));
    }
}
