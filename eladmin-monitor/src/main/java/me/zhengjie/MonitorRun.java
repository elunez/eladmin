package me.zhengjie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *
 * @author: Zhang houying
 * @date: 2019/11/3
 */
@EnableAsync
@SpringBootApplication
public class MonitorRun {

    public static void main(String[] args) {
        SpringApplication.run(MonitorRun.class, args);
    }

}
