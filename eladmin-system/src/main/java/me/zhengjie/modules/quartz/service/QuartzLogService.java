package me.zhengjie.modules.quartz.service;

import me.zhengjie.modules.quartz.domain.QuartzLog;

/**
 * 定时任务日志业务层
 *
 * @author Emil.Zhang
 * 2022-03-24
 */
public interface QuartzLogService {

    /**
     * 创建
     *
     * @param resources /
     */
    void create(QuartzLog resources);
}
