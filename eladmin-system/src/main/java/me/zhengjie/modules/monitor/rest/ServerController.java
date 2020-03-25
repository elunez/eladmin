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

import java.util.Set;

/**
* @author Zhang houying
* @date 2019-11-03
*/
@Api(tags = "服务监控管理")
@RestController
@RequestMapping("/api/server")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping
    @Log("查询服务监控")
    @ApiOperation("查询服务监控")
    @PreAuthorize("@el.check('server:list')")
    public ResponseEntity<Object> getServers(ServerQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(serverService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增服务监控")
    @ApiOperation("新增服务监控")
    @PreAuthorize("@el.check('server:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Server resources){
        return new ResponseEntity<>(serverService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改服务监控")
    @ApiOperation("修改服务监控")
    @PreAuthorize("@el.check('server:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Server resources){
        serverService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除服务监控")
    @ApiOperation("删除服务监控")
    @PreAuthorize("@el.check('server:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Integer> ids){
        serverService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
