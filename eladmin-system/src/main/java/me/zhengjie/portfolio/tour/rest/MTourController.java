/*
*  Copyright 2019-2020 Zheng Jie
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
package me.zhengjie.portfolio.tour.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.portfolio.tour.domain.MTour;
import me.zhengjie.portfolio.tour.service.MTourService;
import me.zhengjie.portfolio.tour.service.dto.MTourQueryCriteria;
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

/**
* @website https://el-admin.vip
* @author smk
* @date 2022-05-03
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "tour管理")
@RequestMapping("/api/mTour")
public class MTourController {

    private final MTourService mTourService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('mTour:list')")
    public void exportMTour(HttpServletResponse response, MTourQueryCriteria criteria) throws IOException {
        mTourService.download(mTourService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询tour")
    @ApiOperation("查询tour")
    @PreAuthorize("@el.check('mTour:list')")
    public ResponseEntity<Object> queryMTour(MTourQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(mTourService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tour")
    @ApiOperation("新增tour")
    @PreAuthorize("@el.check('mTour:add')")
    public ResponseEntity<Object> createMTour(@Validated @RequestBody MTour resources){
        return new ResponseEntity<>(mTourService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tour")
    @ApiOperation("修改tour")
    @PreAuthorize("@el.check('mTour:edit')")
    public ResponseEntity<Object> updateMTour(@Validated @RequestBody MTour resources){
        mTourService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tour")
    @ApiOperation("删除tour")
    @PreAuthorize("@el.check('mTour:del')")
    public ResponseEntity<Object> deleteMTour(@RequestBody Long[] ids) {
        mTourService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}