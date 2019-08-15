package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.bd.domain.SupplierInfo;
import me.zhengjie.modules.wms.bd.service.SupplierInfoService;
import me.zhengjie.modules.wms.bd.service.dto.SupplierInfoQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
* @author jie
* @date 2019-08-03
*/
@Api(tags = "供应商资料管理")
@RestController
@RequestMapping("api")
public class SupplierInfoController {

    @Autowired
    private SupplierInfoService supplierInfoService;

    @Log("分页查询供应商资料列表")
    @ApiOperation(value = "分页查询供应商资料列表")
    @GetMapping(value = "/querySupplierInfoPage")
    @PreAuthorize("hasAnyRole('ADMIN','BDSUPPLIERINFO_ALL','BDSUPPLIERINFO_SELECT')")
    public ResponseEntity querySupplierInfoPage(SupplierInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(supplierInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("初始化供应商编号")
    @ApiOperation(value = "初始化供应商编号")
    @GetMapping(value = "/initSupplierCode")
    @PreAuthorize("hasAnyRole('ADMIN','BDSUPPLIERINFO_ALL','BDSUPPLIERINFO_SELECT')")
    public ResponseEntity initSupplierCode(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");//设置日期格式
        String supplierCode = "GYS"+ LocalDateTime.now().format(fmt);
        return new ResponseEntity(supplierCode,HttpStatus.OK);
    }


    @Log("查询供应商资料列表")
    @ApiOperation(value = "查询供应商资料列表")
    @GetMapping(value = "/querySupplierInfoList")
    @PreAuthorize("hasAnyRole('ADMIN','BDSUPPLIERINFO_ALL','BDSUPPLIERINFO_SELECT')")
    public ResponseEntity querySupplierInfoList(SupplierInfoQueryCriteria criteria){
        return new ResponseEntity(supplierInfoService.queryAll(criteria),HttpStatus.OK);
    }

    @Log("查询供应商资料列表")
    @ApiOperation(value = "分页查询供应商资料列表")
    @GetMapping(value = "/supplierInfo/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','BDSUPPLIERINFO_ALL','BDSUPPLIERINFO_SELECT')")
    public ResponseEntity findSupplierInfo(@PathVariable("id") Long id){
        return new ResponseEntity(supplierInfoService.findById(id),HttpStatus.OK);
    }

    @Log("新增供应商资料")
    @ApiOperation(value = "新增供应商资料")
    @PostMapping(value = "/supplierInfo")
    @PreAuthorize("hasAnyRole('ADMIN','BDSUPPLIERINFO_ALL','BDSUPPLIERINFO_CREATE')")
    public ResponseEntity create(@Validated @RequestBody SupplierInfo resources){
        return new ResponseEntity(supplierInfoService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改供应商资料")
    @ApiOperation(value = "修改供应商资料")
    @PutMapping(value = "/supplierInfo")
    @PreAuthorize("hasAnyRole('ADMIN','BDSUPPLIERINFO_ALL','BDSUPPLIERINFO_EDIT')")
    public ResponseEntity update(@Validated @RequestBody SupplierInfo resources){
        supplierInfoService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除供应商资料")
    @ApiOperation(value = "删除供应商资料")
    @DeleteMapping(value = "/supplierInfo/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','BDSUPPLIERINFO_ALL','BDSUPPLIERINFO_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        supplierInfoService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}