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
import com.srr.domain.Club;
import com.srr.service.ClubService;
import com.srr.service.dto.ClubQueryCriteria;
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
import com.srr.service.dto.ClubDto;

/**
* @website https://eladmin.vip
* @author Chanheng
* @date 2025-05-18
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "clubs")
@RequestMapping("/api/club")
public class ClubController {

    private final ClubService clubService;

    @ApiOperation("Export Data")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('club:list')")
    public void exportClub(HttpServletResponse response, ClubQueryCriteria criteria) throws IOException {
        clubService.download(clubService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("Query clubs")
    @PreAuthorize("@el.check('club:list')")
    public ResponseEntity<PageResult<ClubDto>> queryClub(ClubQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(clubService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("Add clubs")
    @ApiOperation("Add clubs")
    @PreAuthorize("@el.check('club:add')")
    public ResponseEntity<Object> createClub(@Validated @RequestBody Club resources){
        clubService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("Edit clubs")
    @ApiOperation("Edit clubs")
    @PreAuthorize("@el.check('club:edit')")
    public ResponseEntity<Object> updateClub(@Validated @RequestBody Club resources){
        clubService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("Delete clubs")
    @ApiOperation("Delete clubs")
    @PreAuthorize("@el.check('club:del')")
    public ResponseEntity<Object> deleteClub(@ApiParam(value = "Pass ID array []") @RequestBody Long[] ids) {
        clubService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}