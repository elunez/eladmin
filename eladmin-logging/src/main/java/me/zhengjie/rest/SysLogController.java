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
import me.zhengjie.annotation.Log;
import me.zhengjie.service.SysLogService;
import me.zhengjie.service.dto.SysLogQueryCriteria;
import me.zhengjie.service.dto.SysLogSmallDto;
import me.zhengjie.utils.PageResult;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logs")
@Api(tags = "System: Log Management")
public class SysLogController {

    private final SysLogService sysLogService;

    @Log("Export Data")
    @ApiOperation("Export Data")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check()")
    public void exportLog(HttpServletResponse response, SysLogQueryCriteria criteria) throws IOException {
        criteria.setLogType("INFO");
        sysLogService.download(sysLogService.queryAll(criteria), response);
    }

    @Log("Export Error Data")
    @ApiOperation("Export Error Data")
    @GetMapping(value = "/error/download")
    @PreAuthorize("@el.check()")
    public void exportErrorLog(HttpServletResponse response, SysLogQueryCriteria criteria) throws IOException {
        criteria.setLogType("ERROR");
        sysLogService.download(sysLogService.queryAll(criteria), response);
    }
    @GetMapping
    @ApiOperation("Log Query")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> queryLog(SysLogQueryCriteria criteria, Pageable pageable){
        criteria.setLogType("INFO");
        return new ResponseEntity<>(sysLogService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    @ApiOperation("User Log Query")
    public ResponseEntity<PageResult<SysLogSmallDto>> queryUserLog(SysLogQueryCriteria criteria, Pageable pageable){
        criteria.setLogType("INFO");
        criteria.setUsername(SecurityUtils.getCurrentUsername());
        return new ResponseEntity<>(sysLogService.queryAllByUser(criteria,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error")
    @ApiOperation("Error Log Query")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> queryErrorLog(SysLogQueryCriteria criteria, Pageable pageable){
        criteria.setLogType("ERROR");
        return new ResponseEntity<>(sysLogService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error/{id}")
    @ApiOperation("Log Exception Detail Query")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> queryErrorLogDetail(@PathVariable Long id){
        return new ResponseEntity<>(sysLogService.findByErrDetail(id), HttpStatus.OK);
    }
    @DeleteMapping(value = "/del/error")
    @Log("Delete All ERROR Logs")
    @ApiOperation("Delete All ERROR Logs")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> delAllErrorLog(){
        sysLogService.delAllByError();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/info")
    @Log("Delete All INFO Logs")
    @ApiOperation("Delete All INFO Logs")
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> delAllInfoLog(){
        sysLogService.delAllByInfo();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
