package me.zhengjie.modules.system.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.system.domain.NwIncidents;
import me.zhengjie.modules.system.service.NwIncidentsService;
import me.zhengjie.modules.system.service.dto.NwIncidentsQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author Bobby Kimutai
* @date 2019-08-05
*/
@Api(tags = "NwIncidents management")
@RestController
@RequestMapping("api")
public class NwIncidentsController {

    @Autowired
    private NwIncidentsService nwIncidentsService;

    @Log("查询NwIncidents")
    @ApiOperation(value = "查询NwIncidents")
    @GetMapping(value = "/nwIncidents")
    @PreAuthorize("hasAnyRole('ADMIN','NWINCIDENTS_ALL','NWINCIDENTS_SELECT')")
    public ResponseEntity getNwIncidentss(NwIncidentsQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(nwIncidentsService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增NwIncidents")
    @ApiOperation(value = "新增NwIncidents")
    @PostMapping(value = "/nwIncidents")
    @PreAuthorize("hasAnyRole('ADMIN','NWINCIDENTS_ALL','NWINCIDENTS_CREATE')")
    public ResponseEntity create(@Validated @RequestBody NwIncidents resources){
        return new ResponseEntity(nwIncidentsService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改NwIncidents")
    @ApiOperation(value = "修改NwIncidents")
    @PutMapping(value = "/nwIncidents")
    @PreAuthorize("hasAnyRole('ADMIN','NWINCIDENTS_ALL','NWINCIDENTS_EDIT')")
    public ResponseEntity update(@Validated @RequestBody NwIncidents resources){
        nwIncidentsService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除NwIncidents")
    @ApiOperation(value = "删除NwIncidents")
    @DeleteMapping(value = "/nwIncidents/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','NWINCIDENTS_ALL','NWINCIDENTS_DELETE')")
    public ResponseEntity delete(@PathVariable Integer id){
        nwIncidentsService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}