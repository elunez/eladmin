package me.zhengjie.service;

import me.zhengjie.domain.Log;
import me.zhengjie.service.dto.LogQueryCriteria;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
public interface LogService {

    Object queryAll(LogQueryCriteria criteria, Pageable pageable);

    List<Log> queryAll(LogQueryCriteria criteria);

    Object queryAllByUser(LogQueryCriteria criteria, Pageable pageable);

    @Async
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, Log log);

    /**
     * 查询异常详情
     * @param id 日志ID
     * @return Object
     */
    Object findByErrDetail(Long id);

    void download(List<Log> queryAll, HttpServletResponse response) throws IOException;
}
