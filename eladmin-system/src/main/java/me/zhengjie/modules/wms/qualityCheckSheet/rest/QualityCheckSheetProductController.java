package me.zhengjie.modules.wms.qualityCheckSheet.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.qualityCheckSheet.domain.QualityCheckSheetProduct;
import me.zhengjie.modules.wms.qualityCheckSheet.service.QualityCheckSheetProductService;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetProductQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author huangxingxing
* @date 2019-11-12
*/
@Api(tags = "QualityCheckSheetProduct管理")
@RestController
@RequestMapping("api")
public class QualityCheckSheetProductController {

    @Autowired
    private QualityCheckSheetProductService qualityCheckSheetProductService;

    @Log("查询QualityCheckSheetProduct")
    @ApiOperation(value = "查询QualityCheckSheetProduct")
    @GetMapping(value = "/qualityCheckSheetProduct")
    @PreAuthorize("hasAnyRole('ADMIN','QUALITYCHECKSHEETPRODUCT_ALL','QUALITYCHECKSHEETPRODUCT_SELECT')")
    public ResponseEntity getQualityCheckSheetProducts(QualityCheckSheetProductQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(qualityCheckSheetProductService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增QualityCheckSheetProduct")
    @ApiOperation(value = "新增QualityCheckSheetProduct")
    @PostMapping(value = "/qualityCheckSheetProduct")
    @PreAuthorize("hasAnyRole('ADMIN','QUALITYCHECKSHEETPRODUCT_ALL','QUALITYCHECKSHEETPRODUCT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody QualityCheckSheetProduct resources){
        return new ResponseEntity(qualityCheckSheetProductService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改QualityCheckSheetProduct")
    @ApiOperation(value = "修改QualityCheckSheetProduct")
    @PutMapping(value = "/qualityCheckSheetProduct")
    @PreAuthorize("hasAnyRole('ADMIN','QUALITYCHECKSHEETPRODUCT_ALL','QUALITYCHECKSHEETPRODUCT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody QualityCheckSheetProduct resources){
        qualityCheckSheetProductService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除QualityCheckSheetProduct")
    @ApiOperation(value = "删除QualityCheckSheetProduct")
    @DeleteMapping(value = "/qualityCheckSheetProduct/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','QUALITYCHECKSHEETPRODUCT_ALL','QUALITYCHECKSHEETPRODUCT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        qualityCheckSheetProductService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}