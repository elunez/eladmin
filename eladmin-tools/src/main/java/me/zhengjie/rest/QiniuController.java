package me.zhengjie.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.aop.log.Log;
import me.zhengjie.domain.QiniuConfig;
import me.zhengjie.domain.QiniuContent;
import me.zhengjie.service.QiNiuService;
import me.zhengjie.service.dto.QiniuQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送邮件
 * @author 郑杰
 * @date 2018/09/28 6:55:53
 */
@Slf4j
@RestController
@RequestMapping("/api/qiNiuContent")
@Api(tags = "工具：七牛云存储管理")
public class QiniuController {

    private final QiNiuService qiNiuService;

    public QiniuController(QiNiuService qiNiuService) {
        this.qiNiuService = qiNiuService;
    }

    @GetMapping(value = "/config")
    public ResponseEntity get(){
        return new ResponseEntity<>(qiNiuService.find(), HttpStatus.OK);
    }

    @Log("配置七牛云存储")
    @ApiOperation("配置七牛云存储")
    @PutMapping(value = "/config")
    public ResponseEntity emailConfig(@Validated @RequestBody QiniuConfig qiniuConfig){
        qiNiuService.update(qiniuConfig);
        qiNiuService.update(qiniuConfig.getType());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download/list")
    public void download(HttpServletResponse response, QiniuQueryCriteria criteria) throws IOException {
        qiNiuService.downloadList(qiNiuService.queryAll(criteria), response);
    }

    @Log("查询文件")
    @ApiOperation("查询文件")
    @GetMapping
    public ResponseEntity getRoles(QiniuQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(qiNiuService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("上传文件")
    @ApiOperation("上传文件")
    @PostMapping
    public ResponseEntity upload(@RequestParam MultipartFile file){
        QiniuContent qiniuContent = qiNiuService.upload(file,qiNiuService.find());
        Map<String,Object> map = new HashMap<>(3);
        map.put("id",qiniuContent.getId());
        map.put("errno",0);
        map.put("data",new String[]{qiniuContent.getUrl()});
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @Log("同步七牛云数据")
    @ApiOperation("同步七牛云数据")
    @PostMapping(value = "/synchronize")
    public ResponseEntity synchronize(){
        qiNiuService.synchronize(qiNiuService.find());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("下载文件")
    @ApiOperation("下载文件")
    @GetMapping(value = "/download/{id}")
    public ResponseEntity download(@PathVariable Long id){
        Map<String,Object> map = new HashMap<>(1);
        map.put("url", qiNiuService.download(qiNiuService.findByContentId(id),qiNiuService.find()));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @Log("删除文件")
    @ApiOperation("删除文件")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        qiNiuService.delete(qiNiuService.findByContentId(id),qiNiuService.find());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("删除多张图片")
    @ApiOperation("删除多张图片")
    @DeleteMapping
    public ResponseEntity deleteAll(@RequestBody Long[] ids) {
        qiNiuService.deleteAll(ids, qiNiuService.find());
        return new ResponseEntity(HttpStatus.OK);
    }
}
