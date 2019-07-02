package me.zhengjie.modules.quartz.service;

import me.zhengjie.modules.quartz.domain.QuartzJob;
import me.zhengjie.modules.quartz.domain.QuartzLog;
import me.zhengjie.modules.quartz.service.dto.JobQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
 * @author Zheng Jie
 * @date 2019-01-07
 */
@CacheConfig(cacheNames = "quartzJob")
public interface QuartzJobService {

    /**
     * queryAll quartzJob
     * @param criteria
     * @param pageable
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(JobQueryCriteria criteria, Pageable pageable);

    /**
     * queryAll quartzLog
     * @param criteria
     * @param pageable
     * @return
     */
    Object queryAllLog(JobQueryCriteria criteria, Pageable pageable);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    QuartzJob create(QuartzJob resources);

    /**
     * update
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    void update(QuartzJob resources);

    /**
     * del
     * @param quartzJob
     */
    @CacheEvict(allEntries = true)
    void delete(QuartzJob quartzJob);

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    QuartzJob findById(Long id);

    /**
     * 更改定时任务状态
     * @param quartzJob
     */
    @CacheEvict(allEntries = true)
    void updateIsPause(QuartzJob quartzJob);

    /**
     * 立即执行定时任务
     * @param quartzJob
     */
    void execution(QuartzJob quartzJob);
}
