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
package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.service.MonitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Zheng Jie
 * @date 2020-05-02
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "系统-服务监控管理")
@RequestMapping("/api/monitor")
public class MonitorController {

    private final MonitorService serverService;

    @GetMapping
    @ApiOperation("查询服务监控")
    @PreAuthorize("@el.check('monitor:list')")
    public ResponseEntity<Object> queryMonitor(){
        return new ResponseEntity<>(serverService.getServers(),HttpStatus.OK);
    }
}
