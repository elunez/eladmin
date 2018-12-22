package me.zhengjie.monitor.service;

import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jie
 * @date 2018-12-13
 */
public interface VisitsService {

    /**
     * 新增记录
     * @param request
     */
    @Async
    void save(HttpServletRequest request);

    /**
     * 获取数据
     * @return
     */
    Object get();

    /**
     * getChartData
     * @return
     */
    Object getChartData();
}
