package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.bd.domain.ConsumablesInfo;
import me.zhengjie.modules.wms.bd.service.ConsumablesInfoService;
import me.zhengjie.modules.wms.bd.service.dto.ConsumablesInfoQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author jie
* @date 2019-10-05
*/
@Api(tags = "耗材管理")
@RestController
@RequestMapping("api")
public class ConsumablesInfoController {

    @Autowired
    private ConsumablesInfoService consumablesInfoService;

    @Log("分页查询耗材列表")
    @ApiOperation(value = "分页查询耗材列表")
    @GetMapping(value = "/queryConsumablesInfoPageList")
    @PreAuthorize("hasAnyRole('ADMIN','BDCONSUMABLESINFO_ALL','BDCONSUMABLESINFO_SELECT')")
    public ResponseEntity queryConsumablesInfoPageList(ConsumablesInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(consumablesInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("分页查询耗材列表")
    @ApiOperation(value = "分页查询耗材列表")
    @GetMapping(value = "/queryConsumablesInfoList")
    @PreAuthorize("hasAnyRole('ADMIN','BDCONSUMABLESINFO_ALL','BDCONSUMABLESINFO_SELECT')")
    public ResponseEntity queryConsumablesInfoList(ConsumablesInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(consumablesInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增耗材")
    @ApiOperation(value = "新增耗材")
    @PostMapping(value = "/consumablesInfo")
    @PreAuthorize("hasAnyRole('ADMIN','BDCONSUMABLESINFO_ALL','BDCONSUMABLESINFO_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ConsumablesInfo resources){
        return new ResponseEntity(consumablesInfoService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改耗材信息")
    @ApiOperation(value = "修改耗材信息")
    @PutMapping(value = "/consumablesInfo")
    @PreAuthorize("hasAnyRole('ADMIN','BDCONSUMABLESINFO_ALL','BDCONSUMABLESINFO_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ConsumablesInfo resources){
        consumablesInfoService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除耗材")
    @ApiOperation(value = "删除耗材")
    @DeleteMapping(value = "/consumablesInfo/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','BDCONSUMABLESINFO_ALL','BDCONSUMABLESINFO_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        consumablesInfoService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查看耗材详情")
    @GetMapping(value = "/consumablesInfo/{id}")
    public ResponseEntity getConsumablesInfo(@PathVariable Long id){
        return new ResponseEntity(consumablesInfoService.findById(id), HttpStatus.OK);
    }
}