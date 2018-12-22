package me.zhengjie.monitor.service;

import me.zhengjie.monitor.domain.Logging;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

/**
 * @author jie
 * @date 2018-11-24
 */
public interface LoggingService {

    /**
     * 新增日志
     * @param joinPoint
     * @param logging
     */
    @Async
    void save(ProceedingJoinPoint joinPoint, Logging logging);
}
