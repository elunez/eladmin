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
import com.srr.domain.Event;
import com.srr.service.EventService;
import com.srr.service.dto.EventQueryCriteria;
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
import com.srr.service.dto.EventDto;

/**
* @website https://eladmin.vip
* @author Chanheng
* @date 2025-05-18
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "event")
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;

    @ApiOperation("Export Data")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('event:list')")
    public void exportEvent(HttpServletResponse response, EventQueryCriteria criteria) throws IOException {
        eventService.download(eventService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("Query event")
    @PreAuthorize("@el.check('event:list')")
    public ResponseEntity<PageResult<EventDto>> queryEvent(EventQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(eventService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("Add event")
    @ApiOperation("Add event")
    @PreAuthorize("@el.check('event:add')")
    public ResponseEntity<Object> createEvent(@Validated @RequestBody Event resources){
        eventService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("Modify event")
    @ApiOperation("Modify event")
    @PreAuthorize("@el.check('event:edit')")
    public ResponseEntity<Object> updateEvent(@Validated @RequestBody Event resources){
        eventService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("Delete event")
    @ApiOperation("Delete event")
    @PreAuthorize("@el.check('event:del')")
    public ResponseEntity<Object> deleteEvent(@ApiParam(value = "Pass ID array[]") @RequestBody Long[] ids) {
        eventService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}