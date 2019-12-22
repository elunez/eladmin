package me.zhengjie.modules.quartz.service;

import me.zhengjie.modules.quartz.domain.QuartzJob;
import me.zhengjie.modules.quartz.domain.QuartzLog;
import me.zhengjie.modules.quartz.service.dto.JobQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2019-01-07
 */
public interface QuartzJobService {

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(JobQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部
     * @param criteria 条件
     * @return /
     */
    List<QuartzJob> queryAll(JobQueryCriteria criteria);

    /**
     * 分页查询日志
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAllLog(JobQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部
     * @param criteria 条件
     * @return /
     */
    List<QuartzLog> queryAllLog(JobQueryCriteria criteria);

    /**
     * 创建
     * @param resources /
     * @return /
     */
    QuartzJob create(QuartzJob resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(QuartzJob resources);

    /**
     * 删除任务
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 根据ID查询
     * @param id ID
     * @return /
     */
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

    /**
     * 导出定时任务
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<QuartzJob> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 导出定时任务日志
     * @param queryAllLog 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void downloadLog(List<QuartzLog> queryAllLog, HttpServletResponse response) throws IOException;
}
