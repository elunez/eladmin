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
import com.srr.domain.Court;
import com.srr.service.CourtService;
import com.srr.service.dto.CourtQueryCriteria;
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
import com.srr.service.dto.CourtDto;

/**
* @website https://eladmin.vip
* @author Chanheng
* @date 2025-05-18
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "court")
@RequestMapping("/api/court")
public class CourtController {

    private final CourtService courtService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('court:list')")
    public void exportCourt(HttpServletResponse response, CourtQueryCriteria criteria) throws IOException {
        courtService.download(courtService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询court")
    @PreAuthorize("@el.check('court:list')")
    public ResponseEntity<PageResult<CourtDto>> queryCourt(CourtQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(courtService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增court")
    @ApiOperation("新增court")
    @PreAuthorize("@el.check('court:add')")
    public ResponseEntity<Object> createCourt(@Validated @RequestBody Court resources){
        courtService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改court")
    @ApiOperation("修改court")
    @PreAuthorize("@el.check('court:edit')")
    public ResponseEntity<Object> updateCourt(@Validated @RequestBody Court resources){
        courtService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除court")
    @ApiOperation("删除court")
    @PreAuthorize("@el.check('court:del')")
    public ResponseEntity<Object> deleteCourt(@ApiParam(value = "传ID数组[]") @RequestBody Long[] ids) {
        courtService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}