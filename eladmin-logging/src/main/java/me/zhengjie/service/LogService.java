package me.zhengjie.service;

import me.zhengjie.domain.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

/**
 * @author jie
 * @date 2018-11-24
 */
public interface LogService {

    /**
     * 新增日志
     * @param joinPoint
     * @param log
     */
    @Async
    void save(ProceedingJoinPoint joinPoint, Log log);

    /**
     * 查询异常详情
     * @param id
     * @return
     */
    Object findByErrDetail(Long id);
}
