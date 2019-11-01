package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Job;
import me.zhengjie.modules.system.service.dto.JobDTO;
import me.zhengjie.modules.system.service.dto.JobQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
public interface JobService {

    JobDTO findById(Long id);

    JobDTO create(Job resources);

    void update(Job resources);

    void delete(Long id);

    Map<String,Object> queryAll(JobQueryCriteria criteria, Pageable pageable);

    List<JobDTO> queryAll(JobQueryCriteria criteria);

    void download(List<JobDTO> queryAll, HttpServletResponse response) throws IOException;
}