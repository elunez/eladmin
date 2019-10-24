package me.zhengjie.modules.quartz.service;

import me.zhengjie.modules.quartz.domain.QuartzJob;
import me.zhengjie.modules.quartz.service.dto.JobQueryCriteria;
import org.springframework.data.domain.Pageable;

/**
 * @author Zheng Jie
 * @date 2019-01-07
 */
public interface QuartzJobService {

    Object queryAll(JobQueryCriteria criteria, Pageable pageable);

    Object queryAllLog(JobQueryCriteria criteria, Pageable pageable);

    QuartzJob create(QuartzJob resources);

    void update(QuartzJob resources);

    void delete(QuartzJob quartzJob);

    QuartzJob findById(Long id);

    /**
     * 更改定时任务状态
     * @param quartzJob /
     */
    void updateIsPause(QuartzJob quartzJob);

    /**
     * 立即执行定时任务
     * @param quartzJob /
     */
    void execution(QuartzJob quartzJob);
}
