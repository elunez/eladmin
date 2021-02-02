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

import cn.hutool.core.collection.CollectionUtil;
import com.uueo.itam.ticket.service.dto.EventTypeDto;
import me.zhengjie.annotation.Log;
import com.uueo.itam.ticket.domain.EventType;
import com.uueo.itam.ticket.service.EventTypeService;
import com.uueo.itam.ticket.service.dto.EventTypeQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author esinhee
* @date 2021-01-20
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "事件类型管理")
@RequestMapping("/api/eventType")
public class EventTypeController {

    private final EventTypeService eventTypeService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('eventType:list')")
    public void download(HttpServletResponse response, EventTypeQueryCriteria criteria) throws IOException {
        eventTypeService.download(eventTypeService.queryAll(criteria), response);
    }

    @ApiOperation("获取全部类型")
    @GetMapping(value = "/lazy")
    @Log("获取全部类型")
    @PreAuthorize("@el.check('eventType:list')")
    public ResponseEntity<Object> query(@RequestParam Integer pid) {
        return new ResponseEntity<>(eventTypeService.getEventTypes(pid),HttpStatus.OK);
    }

    @GetMapping
    @Log("查询事件类型")
    @ApiOperation("查询事件类型")
    @PreAuthorize("@el.check('eventType:list')")
    public ResponseEntity<Object> query(EventTypeQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(eventTypeService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @ApiOperation("根据ID查询上级目录")
    @PostMapping("/superior")
    @PreAuthorize("@el.check('eventType:list')")
    public ResponseEntity<Object> getSuperior(@RequestBody List<Integer> ids) {
        Set<EventTypeDto> etDtos = new LinkedHashSet<>();
        if (CollectionUtil.isNotEmpty(ids)) {
            for (Integer id : ids) {
                EventTypeDto etDto = eventTypeService.findById(id);
                etDtos.addAll(eventTypeService.getSuperior(etDto, new ArrayList<>()));
            }
            return new ResponseEntity<>(eventTypeService.buildTree(new ArrayList<>(etDtos)),HttpStatus.OK);
        }
        return new ResponseEntity<>(eventTypeService.getEventTypes(null),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增事件类型")
    @ApiOperation("新增事件类型")
    @PreAuthorize("@el.check('eventType:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody EventType resources){
        return new ResponseEntity<>(eventTypeService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改事件类型")
    @ApiOperation("修改事件类型")
    @PreAuthorize("@el.check('eventType:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody EventType resources){
        eventTypeService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除事件类型")
    @ApiOperation("删除事件类型")
    @PreAuthorize("@el.check('eventType:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        eventTypeService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}