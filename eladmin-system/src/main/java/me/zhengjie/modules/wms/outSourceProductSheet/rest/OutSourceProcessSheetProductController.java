package me.zhengjie.modules.wms.outSourceProductSheet.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.service.OutSourceProcessSheetProductService;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductQueryCriteria;
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
* @date 2019-08-17
*/
@Api(tags = "SOutSourceProcessSheetProduct管理")
@RestController
@RequestMapping("api")
public class OutSourceProcessSheetProductController {

    @Autowired
    private OutSourceProcessSheetProductService outSourceProcessSheetProductService;

    @Log("查询SOutSourceProcessSheetProduct")
    @ApiOperation(value = "查询SOutSourceProcessSheetProduct")
    @GetMapping(value = "/sOutSourceProcessSheetProduct")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEPROCESSSHEETPRODUCT_ALL','SOUTSOURCEPROCESSSHEETPRODUCT_SELECT')")
    public ResponseEntity getSOutSourceProcessSheetProducts(OutSourceProcessSheetProductQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(outSourceProcessSheetProductService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增SOutSourceProcessSheetProduct")
    @ApiOperation(value = "新增SOutSourceProcessSheetProduct")
    @PostMapping(value = "/sOutSourceProcessSheetProduct")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEPROCESSSHEETPRODUCT_ALL','SOUTSOURCEPROCESSSHEETPRODUCT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody OutSourceProcessSheetProduct resources){
        return new ResponseEntity(outSourceProcessSheetProductService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改SOutSourceProcessSheetProduct")
    @ApiOperation(value = "修改SOutSourceProcessSheetProduct")
    @PutMapping(value = "/sOutSourceProcessSheetProduct")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEPROCESSSHEETPRODUCT_ALL','SOUTSOURCEPROCESSSHEETPRODUCT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody OutSourceProcessSheetProduct resources){
        outSourceProcessSheetProductService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除SOutSourceProcessSheetProduct")
    @ApiOperation(value = "删除SOutSourceProcessSheetProduct")
    @DeleteMapping(value = "/sOutSourceProcessSheetProduct/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEPROCESSSHEETPRODUCT_ALL','SOUTSOURCEPROCESSSHEETPRODUCT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        outSourceProcessSheetProductService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}