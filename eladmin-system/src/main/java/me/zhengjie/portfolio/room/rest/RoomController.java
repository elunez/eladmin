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
package me.zhengjie.portfolio.room.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.portfolio.room.domain.Room;
import me.zhengjie.portfolio.room.service.RoomService;
import me.zhengjie.portfolio.room.service.dto.RoomQueryCriteria;
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
* @author Chanheng
* @date 2022-05-01
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "room管理")
@RequestMapping("/api/room")
public class RoomController {

    private final RoomService roomService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('room:list')")
    public void exportRoom(HttpServletResponse response, RoomQueryCriteria criteria) throws IOException {
        roomService.download(roomService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询room")
    @ApiOperation("查询room")
    @PreAuthorize("@el.check('room:list')")
    public ResponseEntity<Object> queryRoom(RoomQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(roomService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增room")
    @ApiOperation("新增room")
    @PreAuthorize("@el.check('room:add')")
    public ResponseEntity<Object> createRoom(@Validated @RequestBody Room resources){
        return new ResponseEntity<>(roomService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改room")
    @ApiOperation("修改room")
    @PreAuthorize("@el.check('room:edit')")
    public ResponseEntity<Object> updateRoom(@Validated @RequestBody Room resources){
        roomService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除room")
    @ApiOperation("删除room")
    @PreAuthorize("@el.check('room:del')")
    public ResponseEntity<Object> deleteRoom(@RequestBody Long[] ids) {
        roomService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}