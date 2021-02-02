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
package com.uueo.itam.ticket.rest;

import me.zhengjie.annotation.Log;
import com.uueo.itam.ticket.domain.Event;
import com.uueo.itam.ticket.service.EventService;
import com.uueo.itam.ticket.service.dto.EventQueryCriteria;
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
* @author esinhee
* @date 2021-01-19
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "工单管理管理")
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('event:list')")
    public void download(HttpServletResponse response, EventQueryCriteria criteria) throws IOException {
        eventService.download(eventService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询工单管理")
    @ApiOperation("查询工单管理")
    @PreAuthorize("@el.check('event:list')")
    public ResponseEntity<Object> query(EventQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(eventService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增工单管理")
    @ApiOperation("新增工单管理")
    @PreAuthorize("@el.check('event:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Event resources){
        return new ResponseEntity<>(eventService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改工单管理")
    @ApiOperation("修改工单管理")
    @PreAuthorize("@el.check('event:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Event resources){
        eventService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除工单管理")
    @ApiOperation("删除工单管理")
    @PreAuthorize("@el.check('event:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        eventService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}