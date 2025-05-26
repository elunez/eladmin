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

import com.srr.domain.WaitList;
import com.srr.dto.WaitListDto;
import com.srr.dto.WaitListQueryCriteria;
import com.srr.service.WaitListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
 * @date 2025-05-26
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "Wait List Management")
@RequestMapping("/api/wait-list")
public class WaitListController {

    private final WaitListService waitListService;
    private static final String ENTITY_NAME = "waitList";

    @ApiOperation("Export wait list data")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('waitList:list')")
    public void exportWaitList(HttpServletResponse response, WaitListQueryCriteria criteria) throws IOException {
        waitListService.download(waitListService.queryAll(criteria), response);
    }

    @ApiOperation("Query wait list entries")
    @GetMapping
    @PreAuthorize("@el.check('waitList:list')")
    public ResponseEntity<PageResult<WaitListDto>> queryWaitList(WaitListQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(waitListService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @ApiOperation("Query wait list entries by event ID")
    @GetMapping(value = "/event/{eventId}")
    @PreAuthorize("@el.check('waitList:list')")
    public ResponseEntity<List<WaitListDto>> queryWaitListByEvent(@PathVariable Long eventId) {
        return new ResponseEntity<>(waitListService.findByEventId(eventId), HttpStatus.OK);
    }

    @ApiOperation("Query wait list entries by player ID")
    @GetMapping(value = "/player/{playerId}")
    @PreAuthorize("@el.check('waitList:list')")
    public ResponseEntity<List<WaitListDto>> queryWaitListByPlayer(@PathVariable Long playerId) {
        return new ResponseEntity<>(waitListService.findByPlayerId(playerId), HttpStatus.OK);
    }

    @ApiOperation("Add to wait list")
    @PostMapping
    @PreAuthorize("@el.check('waitList:add')")
    public ResponseEntity<WaitListDto> createWaitList(@Validated @RequestBody WaitList resources) {
        return new ResponseEntity<>(waitListService.create(resources), HttpStatus.CREATED);
    }

    @ApiOperation("Update wait list entry")
    @PutMapping
    @PreAuthorize("@el.check('waitList:edit')")
    public ResponseEntity<Object> updateWaitList(@Validated @RequestBody WaitList resources) {
        waitListService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("Promote player from wait list to participant")
    @PutMapping(value = "/{id}/promote")
    @PreAuthorize("@el.check('waitList:edit')")
    public ResponseEntity<Object> promoteWaitListEntry(@PathVariable Long id) {
        boolean success = waitListService.promoteToParticipant(id);
        return new ResponseEntity<>(success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @ApiOperation("Delete wait list entry")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@el.check('waitList:del')")
    public ResponseEntity<Object> deleteWaitList(@PathVariable Long id) {
        waitListService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
