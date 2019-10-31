package me.zhengjie.modules.monitor.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.monitor.domain.vo.RedisVo;
import me.zhengjie.modules.monitor.service.RedisService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Zheng Jie
 * @date 2018-12-10
 */
@RestController
@RequestMapping("/api/redis")
@Api(tags = "系统：Redis缓存管理")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @Log("查询Redis缓存")
    @GetMapping
    @ApiOperation("查询Redis缓存")
    @PreAuthorize("@el.check('redis:list')")
    public ResponseEntity getRedis(String key, Pageable pageable){
        return new ResponseEntity<>(redisService.findByKey(key,pageable), HttpStatus.OK);
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('redis:list')")
    public void download(HttpServletResponse response, String key) throws IOException {
        redisService.download(redisService.findByKey(key), response);
    }

    @Log("删除Redis缓存")
    @DeleteMapping
    @ApiOperation("删除Redis缓存")
    @PreAuthorize("@el.check('redis:del')")
    public ResponseEntity delete(@RequestBody RedisVo resources){
        redisService.delete(resources.getKey());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("清空Redis缓存")
    @DeleteMapping(value = "/all")
    @ApiOperation("清空Redis缓存")
    @PreAuthorize("@el.check('redis:del')")
    public ResponseEntity deleteAll(){
        redisService.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }
}
