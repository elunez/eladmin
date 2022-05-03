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
package room.rest;

import me.zhengjie.annotation.Log;
import room.domain.MRoom;
import room.service.MRoomService;
import room.service.dto.MRoomQueryCriteria;
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
@Api(tags = "room管理")
@RequestMapping("/api/mRoom")
public class MRoomController {

    private final MRoomService mRoomService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('mRoom:list')")
    public void exportMRoom(HttpServletResponse response, MRoomQueryCriteria criteria) throws IOException {
        mRoomService.download(mRoomService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询room")
    @ApiOperation("查询room")
    @PreAuthorize("@el.check('mRoom:list')")
    public ResponseEntity<Object> queryMRoom(MRoomQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(mRoomService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增room")
    @ApiOperation("新增room")
    @PreAuthorize("@el.check('mRoom:add')")
    public ResponseEntity<Object> createMRoom(@Validated @RequestBody MRoom resources){
        return new ResponseEntity<>(mRoomService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改room")
    @ApiOperation("修改room")
    @PreAuthorize("@el.check('mRoom:edit')")
    public ResponseEntity<Object> updateMRoom(@Validated @RequestBody MRoom resources){
        mRoomService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除room")
    @ApiOperation("删除room")
    @PreAuthorize("@el.check('mRoom:del')")
    public ResponseEntity<Object> deleteMRoom(@RequestBody Long[] ids) {
        mRoomService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}