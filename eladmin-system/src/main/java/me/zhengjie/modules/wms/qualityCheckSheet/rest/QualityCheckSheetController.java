package me.zhengjie.modules.wms.qualityCheckSheet.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.qualityCheckSheet.domain.QualityCheckSheet;
import me.zhengjie.modules.wms.qualityCheckSheet.service.QualityCheckSheetService;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetQueryCriteria;
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
@Api(tags = "QualityCheckSheet管理")
@RestController
@RequestMapping("api")
public class QualityCheckSheetController {

    @Autowired
    private QualityCheckSheetService qualityCheckSheetService;

    @Log("查询QualityCheckSheet")
    @ApiOperation(value = "查询QualityCheckSheet")
    @GetMapping(value = "/qualityCheckSheet")
    @PreAuthorize("hasAnyRole('ADMIN','QUALITYCHECKSHEET_ALL','QUALITYCHECKSHEET_SELECT')")
    public ResponseEntity getQualityCheckSheets(QualityCheckSheetQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(qualityCheckSheetService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增QualityCheckSheet")
    @ApiOperation(value = "新增QualityCheckSheet")
    @PostMapping(value = "/qualityCheckSheet")
    @PreAuthorize("hasAnyRole('ADMIN','QUALITYCHECKSHEET_ALL','QUALITYCHECKSHEET_CREATE')")
    public ResponseEntity create(@Validated @RequestBody QualityCheckSheet resources){
        return new ResponseEntity(qualityCheckSheetService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改QualityCheckSheet")
    @ApiOperation(value = "修改QualityCheckSheet")
    @PutMapping(value = "/qualityCheckSheet")
    @PreAuthorize("hasAnyRole('ADMIN','QUALITYCHECKSHEET_ALL','QUALITYCHECKSHEET_EDIT')")
    public ResponseEntity update(@Validated @RequestBody QualityCheckSheet resources){
        qualityCheckSheetService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除QualityCheckSheet")
    @ApiOperation(value = "删除QualityCheckSheet")
    @DeleteMapping(value = "/qualityCheckSheet/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','QUALITYCHECKSHEET_ALL','QUALITYCHECKSHEET_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        qualityCheckSheetService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}