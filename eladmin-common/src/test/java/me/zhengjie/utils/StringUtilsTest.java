package me.zhengjie.utils;

import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

import static me.zhengjie.utils.StringUtils.getIp;
import static me.zhengjie.utils.StringUtils.getWeekDay;
import static me.zhengjie.utils.StringUtils.toCamelCase;
import static me.zhengjie.utils.StringUtils.toCapitalizeCamelCase;
import static me.zhengjie.utils.StringUtils.toUnderScoreCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class StringUtilsTest {

    private static StaticApplicationContext context;
    private static SpringBeanHolder holder;

    @BeforeAll
    public static void setUpContext() {
        // StringUtils 的静态初始化需要 IP 查询组件。
        context = new StaticApplicationContext();
        context.getBeanFactory().registerSingleton("ip2regionSearcher", mock(Ip2regionSearcher.class));
        holder = new SpringBeanHolder();
        holder.setApplicationContext(context);
    }

    @AfterAll
    public static void tearDownContext() {
        holder.destroy();
        context.close();
    }

    @Test
    public void testToCamelCase() {
        assertNull(toCamelCase(null));
    }

    @Test
    public void testToCapitalizeCamelCase() {
        assertNull(StringUtils.toCapitalizeCamelCase(null));
        assertEquals("HelloWorld", toCapitalizeCamelCase("hello_world"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "_", "___"})
    public void testToCapitalizeCamelCaseWithEmptyResult(String value) {
        assertEquals("", toCapitalizeCamelCase(value));
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
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
        assertEquals(simpleDateformat.format(new Date()), getWeekDay());
    }

    @Test
    public void testGetIP() {
        assertEquals("127.0.0.1", getIp(new MockHttpServletRequest()));
    }
}
