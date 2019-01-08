package me.zhengjie.quartz.rest;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.common.aop.log.Log;
import me.zhengjie.common.exception.BadRequestException;
import me.zhengjie.quartz.domain.QuartzJob;
import me.zhengjie.quartz.domain.QuartzLog;
import me.zhengjie.quartz.service.QuartzJobService;
import me.zhengjie.quartz.service.query.QuartzJobQueryService;
import me.zhengjie.quartz.service.query.QuartzLogQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author jie
 * @date 2019-01-07
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class QuartzJobController {

    private static final String ENTITY_NAME = "quartzJob";

    @Autowired
    private QuartzJobService quartzJobService;

    @Autowired
    private QuartzJobQueryService quartzJobQueryService;

    @Autowired
    private QuartzLogQueryService quartzLogQueryService;

    @Log(description = "查询定时任务")
    @GetMapping(value = "/jobs")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_SELECT')")
    public ResponseEntity getJobs(QuartzJob resources, Pageable pageable){
        return new ResponseEntity(quartzJobQueryService.queryAll(resources,pageable), HttpStatus.OK);
    }

    /**
     * 查询定时任务日志
     * @param resources
     * @param pageable
     * @return
     */
    @GetMapping(value = "/jobLogs")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_SELECT')")
    public ResponseEntity getJobLogs(QuartzLog resources, Pageable pageable){
        return new ResponseEntity(quartzLogQueryService.queryAll(resources,pageable), HttpStatus.OK);
    }

    @Log(description = "新增定时任务")
    @PostMapping(value = "/jobs")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_CREATE')")
    public ResponseEntity create(@Validated @RequestBody QuartzJob resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(quartzJobService.create(resources),HttpStatus.CREATED);
    }

    @Log(description = "修改定时任务")
    @PutMapping(value = "/jobs")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_EDIT')")
    public ResponseEntity update(@Validated @RequestBody QuartzJob resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        quartzJobService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log(description = "更改定时任务状态")
    @PutMapping(value = "/jobs/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_EDIT')")
    public ResponseEntity updateIsPause(@PathVariable Long id){
        quartzJobService.updateIsPause(quartzJobService.findById(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log(description = "执行定时任务")
    @PutMapping(value = "/jobs/exec/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_EDIT')")
    public ResponseEntity execution(@PathVariable Long id){
        quartzJobService.execution(quartzJobService.findById(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log(description = "删除定时任务")
    @DeleteMapping(value = "/jobs/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','JOB_ALL','JOB_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        quartzJobService.delete(quartzJobService.findById(id));
        return new ResponseEntity(HttpStatus.OK);
    }
}
