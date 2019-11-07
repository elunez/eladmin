package me.zhengjie.modules.monitor.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.monitor.domain.Server;
import me.zhengjie.modules.monitor.service.ServerService;
import me.zhengjie.modules.monitor.service.dto.ServerQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author Zhang houying
* @date 2019-11-03
*/
@Api(tags = "Server管理")
@RestController
@RequestMapping("/api/server")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping
    @Log("查询Server")
    @ApiOperation("查询Server")
    @PreAuthorize("@el.check('server:list')")
    public ResponseEntity getServers(ServerQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(serverService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增Server")
    @ApiOperation("新增Server")
    @PreAuthorize("@el.check('server:add')")
    public ResponseEntity create(@Validated @RequestBody Server resources){
        return new ResponseEntity<>(serverService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改Server")
    @ApiOperation("修改Server")
    @PreAuthorize("@el.check('server:edit')")
    public ResponseEntity update(@Validated @RequestBody Server resources){
        serverService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    @Log("删除Server")
    @ApiOperation("删除Server")
    @PreAuthorize("@el.check('server:del')")
    public ResponseEntity delete(@PathVariable Integer id){
        serverService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
