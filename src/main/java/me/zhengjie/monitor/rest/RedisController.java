package me.zhengjie.monitor.rest;

import me.zhengjie.common.aop.log.Log;
import me.zhengjie.monitor.domain.vo.RedisVo;
import me.zhengjie.monitor.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author jie
 * @date 2018-12-10
 */
@RestController
@RequestMapping("api")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @Log(description = "查询Redis缓存")
    @GetMapping(value = "/redis")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_SELECT')")
    public ResponseEntity getRedis(String key, Pageable pageable){
        return new ResponseEntity(redisService.findByKey(key,pageable), HttpStatus.OK);
    }

    @Log(description = "新增Redis缓存")
    @PostMapping(value = "/redis")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_CREATE')")
    public ResponseEntity create(@Validated @RequestBody RedisVo resources){
        redisService.save(resources);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Log(description = "修改Redis缓存")
    @PutMapping(value = "/redis")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_EDIT')")
    public ResponseEntity update(@Validated @RequestBody RedisVo resources){
        redisService.save(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log(description = "删除Redis缓存")
    @DeleteMapping(value = "/redis/{key}")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_DELETE')")
    public ResponseEntity delete(@PathVariable String key){
        redisService.delete(key);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log(description = "清空Redis缓存")
    @DeleteMapping(value = "/redis/all")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_DELETE')")
    public ResponseEntity deleteAll(){
        redisService.flushdb();
        return new ResponseEntity(HttpStatus.OK);
    }
}
