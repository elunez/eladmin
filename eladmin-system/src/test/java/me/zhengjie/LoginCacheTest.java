package me.zhengjie;

import me.zhengjie.modules.security.service.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginCacheTest {

    @Resource(name = "userDetailsService")
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void testCache() {
        long start1 = System.currentTimeMillis();
        int size = 10000;
        for (int i = 0; i < size; i++) {
            userDetailsService.loadUserByUsername("admin");
        }
        long end1 = System.currentTimeMillis();
        //关闭缓存
        userDetailsService.setEnableCache(false);
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            userDetailsService.loadUserByUsername("admin");
        }
        long end2 = System.currentTimeMillis();
        System.out.print("使用缓存：" + (end1 - start1) + "毫秒\n 不使用缓存：" + (end2 - start2) + "毫秒");
    }
}
