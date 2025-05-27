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

import com.srr.domain.Event;
import com.srr.dto.EventDto;
import com.srr.dto.EventQueryCriteria;
import com.srr.dto.JoinEventDto;
import com.srr.dto.TeamPlayerDto;
import com.srr.enumeration.EventStatus;
import com.srr.service.EventService;
import com.srr.service.TeamPlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.utils.PageResult;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Chanheng
 * @website https://eladmin.vip
 * @date 2025-05-18
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "event")
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;
    private final TeamPlayerService teamPlayerService;

    @ApiOperation("Export Data")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('event:list')")
    public void exportEvent(HttpServletResponse response, EventQueryCriteria criteria) throws IOException {
        eventService.download(eventService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("Query event")
    @PreAuthorize("@el.check('event:list')")
    public ResponseEntity<PageResult<EventDto>> queryEvent(EventQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(eventService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("Add event")
    @ApiOperation("Add event")
    @PreAuthorize("@el.check('event:add')")
    public ResponseEntity<Object> createEvent(@Validated @RequestBody Event resources) {
        final var result = eventService.create(resources);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    @Log("Modify event")
    @ApiOperation("Modify event")
    @PreAuthorize("@el.check('event:edit')")
    public ResponseEntity<Object> updateEvent(@Validated @RequestBody Event resources) {
        final var result = eventService.update(resources);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status/{status}")
    @Log("Update event status")
    @ApiOperation("Update event status")
    @PreAuthorize("@el.check('event:edit')")
    public ResponseEntity<Object> updateEventStatus(
            @PathVariable Long id, 
            @PathVariable EventStatus status) {
        final var result = eventService.updateStatus(id, status);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{id}/join")
    @Log("Join event")
    @ApiOperation("Join event")
    @PreAuthorize("@el.check('event:join')")
    public ResponseEntity<Object> joinEvent(@PathVariable Long id, @RequestBody JoinEventDto joinEventDto) {
        // Ensure ID in path matches ID in DTO
        joinEventDto.setEventId(id);
        final EventDto result = eventService.joinEvent(joinEventDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}/players")
    @ApiOperation("Find all team players in an event")
    @PreAuthorize("@el.check('event:list')")
    public ResponseEntity<List<TeamPlayerDto>> findEventPlayers(@PathVariable("id") Long eventId) {
        return new ResponseEntity<>(teamPlayerService.findByEventId(eventId), HttpStatus.OK);
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