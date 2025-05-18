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
package com.srr.rest;

import me.zhengjie.annotation.Log;
import com.srr.domain.Sport;
import com.srr.service.SportService;
import com.srr.service.dto.SportQueryCriteria;
import me.zhengjie.annotation.rest.AnonymousGetMapping;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import me.zhengjie.utils.PageResult;
import com.srr.service.dto.SportDto;

/**
* @website https://eladmin.vip
* @author Chanheng
* @date 2025-05-17
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "sport")
@RequestMapping("/api/sport")
public class SportController {

    private final SportService sportService;

    @AnonymousGetMapping(value = "/ping")
    public String ping() {
        return "pong";
    }

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('sport:list')")
    public void exportSport(HttpServletResponse response, SportQueryCriteria criteria) throws IOException {
        sportService.download(sportService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询sport")
    @PreAuthorize("@el.check('sport:list')")
    public ResponseEntity<PageResult<SportDto>> querySport(SportQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(sportService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增sport")
    @ApiOperation("新增sport")
    @PreAuthorize("@el.check('sport:add')")
    public ResponseEntity<Object> createSport(@Validated @RequestBody Sport resources){
        sportService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改sport")
    @ApiOperation("修改sport")
    @PreAuthorize("@el.check('sport:edit')")
    public ResponseEntity<Object> updateSport(@Validated @RequestBody Sport resources){
        sportService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除sport")
    @ApiOperation("删除sport")
    @PreAuthorize("@el.check('sport:del')")
    public ResponseEntity<Object> deleteSport(@ApiParam(value = "传ID数组[]") @RequestBody Long[] ids) {
        sportService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}