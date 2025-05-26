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
import me.zhengjie.domain.S3Config;
import me.zhengjie.domain.S3Content;
import me.zhengjie.service.dto.S3QueryCriteria;
import me.zhengjie.service.S3Service;
import me.zhengjie.utils.PageResult;
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
 * Send Email
 * @author Zheng Jie
 * @date 2018/09/28 6:55:53
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3Content")
@Api(tags = "Tools: S3 Cloud Storage Management")
public class S3Controller {

    private final S3Service s3Service;

    @GetMapping(value = "/config")
    public ResponseEntity<S3Config> queryS3Config(){
        return new ResponseEntity<>(s3Service.find(), HttpStatus.OK);
    }

    @Log("Configure S3 Cloud Storage")
    @ApiOperation("Configure S3 Cloud Storage")
    @PutMapping(value = "/config")
    public ResponseEntity<Object> updateS3Config(@Validated @RequestBody S3Config s3Config){
        s3Service.config(s3Config);
        s3Service.update(s3Config.getType());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("Export Data")
    @GetMapping(value = "/download")
    public void exportS3(HttpServletResponse response, S3QueryCriteria criteria) throws IOException {
        s3Service.downloadList(s3Service.queryAll(criteria), response);
    }

    @ApiOperation("Query File")
    @GetMapping
    public ResponseEntity<PageResult<S3Content>> queryS3(S3QueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(s3Service.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @ApiOperation("Upload File")
    @PostMapping
    public ResponseEntity<Object> uploadS3(@RequestParam MultipartFile file){
        S3Content s3Content = s3Service.upload(file,s3Service.find());
        Map<String,Object> map = new HashMap<>(3);
        map.put("id",s3Content.getId());
        map.put("errno",0);
        map.put("data",new String[]{s3Content.getUrl()});
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @Log("Synchronize S3 Cloud Data")
    @ApiOperation("Synchronize S3 Cloud Data")
    @PostMapping(value = "/synchronize")
    public ResponseEntity<Object> synchronizeS3(){
        s3Service.synchronize(s3Service.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("Download File")
    @ApiOperation("Download File")
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<Object> downloadS3(@PathVariable Long id){
        Map<String,Object> map = new HashMap<>(1);
        map.put("url", s3Service.download(s3Service.findByContentId(id),s3Service.find()));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @Log("Delete File")
    @ApiOperation("Delete File")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteS3(@PathVariable Long id){
        s3Service.delete(s3Service.findByContentId(id),s3Service.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("Delete Multiple Images")
    @ApiOperation("Delete Multiple Images")
    @DeleteMapping
    public ResponseEntity<Object> deleteAllS3(@RequestBody Long[] ids) {
        s3Service.deleteAll(ids, s3Service.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
