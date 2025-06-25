/*
 *  Copyright 2019-2025 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.annotation.Log;
import me.zhengjie.config.AmzS3Config;
import me.zhengjie.domain.S3Storage;
import me.zhengjie.service.S3StorageService;
import me.zhengjie.service.dto.S3StorageQueryCriteria;
import me.zhengjie.utils.PageResult;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * amz S3 协议云存储管理
 * @author 郑杰
 * @date 2025-06-25
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3Storage")
@Api(tags = "工具：S3协议云存储管理")
public class S3StorageController {

    private final AmzS3Config amzS3Config;
    private final S3StorageService s3StorageService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storage:list')")
    public void exportS3Storage(HttpServletResponse response, S3StorageQueryCriteria criteria) throws IOException {
        s3StorageService.download(s3StorageService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询文件")
    @PreAuthorize("@el.check('storage:list')")
    public ResponseEntity<PageResult<S3Storage>> queryS3Storage(S3StorageQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(s3StorageService.queryAll(criteria, pageable),HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("上传文件")
    public ResponseEntity<Object> uploadS3Storage(@RequestParam MultipartFile file){
        S3Storage storage = s3StorageService.upload(file);
        Map<String,Object> map = new HashMap<>(3);
        map.put("id",storage.getId());
        map.put("errno",0);
        map.put("data",new String[]{amzS3Config.getDomain() + "/" + storage.getFilePath()});
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @Log("下载文件")
    @ApiOperation("下载文件")
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<Object> downloadS3Storage(@PathVariable Long id){
        Map<String,Object> map = new HashMap<>(1);
        S3Storage storage = s3StorageService.getById(id);
        if (storage == null) {
            map.put("message", "文件不存在或已被删除");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
        // 仅适合公开文件访问，私有文件可以使用服务中的 privateDownload 方法
        String url = amzS3Config.getDomain() + "/" + storage.getFilePath();
        map.put("url", url);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @Log("删除多个文件")
    @DeleteMapping
    @ApiOperation("删除多个文件")
    @PreAuthorize("@el.check('storage:del')")
    public ResponseEntity<Object> deleteAllS3Storage(@RequestBody List<Long> ids) {
        s3StorageService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
