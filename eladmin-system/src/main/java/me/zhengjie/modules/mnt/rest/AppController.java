package me.zhengjie.modules.mnt.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.mnt.domain.App;
import me.zhengjie.modules.mnt.service.AppService;
import me.zhengjie.modules.mnt.service.dto.AppQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Api(tags = "应用管理")
@RestController
@RequestMapping("/api/app")
public class AppController {

    @Autowired
    private AppService appService;

    public AppController(AppService appService){
    	this.appService = this.appService;
	}

    @Log("查询App")
    @ApiOperation(value = "查询App")
    @GetMapping
	@PreAuthorize("@el.check('app:list')")
    public ResponseEntity getApps(AppQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(appService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增App")
    @ApiOperation(value = "新增App")
    @PostMapping
	@PreAuthorize("@el.check('app:add')")
    public ResponseEntity create(@Validated @RequestBody App resources){
        return new ResponseEntity(appService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改App")
    @ApiOperation(value = "修改App")
    @PutMapping
	@PreAuthorize("@el.check('app:edit')")
    public ResponseEntity update(@Validated @RequestBody App resources){
        appService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除App")
    @ApiOperation(value = "删除App")
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("@el.check('app:del')")
    public ResponseEntity delete(@PathVariable Long id){
        appService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
