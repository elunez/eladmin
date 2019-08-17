package me.zhengjie.modules.wms.outSourceProductSheet.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import me.zhengjie.modules.wms.outSourceProductSheet.service.OutSourceProcessSheetService;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetQueryCriteria;
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
@Api(tags = "SOutSourceProcessSheet管理")
@RestController
@RequestMapping("api")
public class OutSourceProcessSheetController {

    @Autowired
    private OutSourceProcessSheetService outSourceProcessSheetService;

    @Log("查询SOutSourceProcessSheet")
    @ApiOperation(value = "查询SOutSourceProcessSheet")
    @GetMapping(value = "/sOutSourceProcessSheet")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEPROCESSSHEET_ALL','SOUTSOURCEPROCESSSHEET_SELECT')")
    public ResponseEntity getSOutSourceProcessSheets(OutSourceProcessSheetQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(outSourceProcessSheetService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增SOutSourceProcessSheet")
    @ApiOperation(value = "新增SOutSourceProcessSheet")
    @PostMapping(value = "/sOutSourceProcessSheet")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEPROCESSSHEET_ALL','SOUTSOURCEPROCESSSHEET_CREATE')")
    public ResponseEntity create(@Validated @RequestBody OutSourceProcessSheet resources){
        return new ResponseEntity(outSourceProcessSheetService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改SOutSourceProcessSheet")
    @ApiOperation(value = "修改SOutSourceProcessSheet")
    @PutMapping(value = "/sOutSourceProcessSheet")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEPROCESSSHEET_ALL','SOUTSOURCEPROCESSSHEET_EDIT')")
    public ResponseEntity update(@Validated @RequestBody OutSourceProcessSheet resources){
        outSourceProcessSheetService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除SOutSourceProcessSheet")
    @ApiOperation(value = "删除SOutSourceProcessSheet")
    @DeleteMapping(value = "/sOutSourceProcessSheet/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEPROCESSSHEET_ALL','SOUTSOURCEPROCESSSHEET_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        outSourceProcessSheetService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}