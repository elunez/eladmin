package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Job;
import me.zhengjie.modules.system.service.dto.JobDTO;
import me.zhengjie.modules.system.service.dto.JobQueryCriteria;
import org.springframework.data.domain.Pageable;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
public interface JobService {

    JobDTO findById(Long id);

    JobDTO create(Job resources);

    void update(Job resources);

    void delete(Long id);

    Object queryAll(JobQueryCriteria criteria, Pageable pageable);
}