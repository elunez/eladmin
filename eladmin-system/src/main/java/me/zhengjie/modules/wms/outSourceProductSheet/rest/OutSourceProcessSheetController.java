package me.zhengjie.modules.wms.outSourceProductSheet.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import me.zhengjie.modules.wms.outSourceProductSheet.request.CreateOutSourceProcessSheetRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.request.QueryOutSourceProcessSheetProductRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.request.UpdateOutSourceProcessSheetRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.service.OutSourceProcessSheetProductService;
import me.zhengjie.modules.wms.outSourceProductSheet.service.OutSourceProcessSheetService;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductQueryCriteria;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetQueryCriteria;
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

/**
* @author jie
* @date 2019-08-17
*/
@Api(tags = "委外加工管理")
@RestController
@RequestMapping("api")
public class OutSourceProcessSheetController {

    @Autowired
    private OutSourceProcessSheetService outSourceProcessSheetService;

    @Autowired
    private OutSourceProcessSheetProductService outSourceProcessSheetProductService;

    @Log("分页查询委外加工单")
    @ApiOperation(value = "分页查询委外加工单")
    @GetMapping(value = "/queryOutSourceProcessSheetPage")
    @PreAuthorize("hasAnyRole('ADMIN','OUT_SOURCE_PROCESS_SHEET_ALL','OUT_SOURCE_PROCESS_SHEET_SELECT')")
    public ResponseEntity queryOutSourceProcessSheetPage(OutSourceProcessSheetQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(outSourceProcessSheetService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增委外加工单")
    @ApiOperation(value = "新增委外加工单")
    @PostMapping(value = "/outSourceProcessSheet")
    @PreAuthorize("hasAnyRole('ADMIN','OUT_SOURCE_PROCESS_SHEET_ALL','OUT_SOURCE_PROCESS_SHEET_CREATE')")
    public ResponseEntity create(@RequestBody CreateOutSourceProcessSheetRequest createOutSourceProcessSheetRequest){
        return new ResponseEntity(outSourceProcessSheetService.create(createOutSourceProcessSheetRequest),HttpStatus.CREATED);
    }

    @Log("修改委外加工单")
    @ApiOperation(value = "修改委外加工单")
    @PutMapping(value = "/outSourceProcessSheet")
    @PreAuthorize("hasAnyRole('ADMIN','OUT_SOURCE_PROCESS_SHEET_ALL','OUT_SOURCE_PROCESS_SHEET_EDIT')")
    public ResponseEntity update(@RequestBody UpdateOutSourceProcessSheetRequest updateOutSourceProcessSheetRequest){
        outSourceProcessSheetService.update(updateOutSourceProcessSheetRequest);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除委外加工单")
    @ApiOperation(value = "删除委外加工单")
    @DeleteMapping(value = "/outSourceProcessSheet/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','OUT_SOURCE_PROCESS_SHEET_ALL','OUT_SOURCE_PROCESS_SHEET_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        outSourceProcessSheetService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }


    @Log("初始化委外加工单编号")
    @ApiOperation(value = "初始化委外加工单编号")
    @GetMapping(value = "/initOutSourceProcessSheetCode")
    public ResponseEntity initOutSourceProcessSheetCode(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");//设置日期格式
        String supplierCode = "OS"+ LocalDateTime.now().format(fmt);
        return new ResponseEntity(supplierCode,HttpStatus.OK);
    }

    @Log("查看委外加工单")
    @ApiOperation(value = "查看委外加工单")
    @GetMapping(value = "/outSourceProcessSheet/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','OUT_SOURCE_PROCESS_SHEET_ALL','OUT_SOURCE_PROCESS_SHEET_DETAIL_BY_ID')")
    public ResponseEntity getOutSourceProcessSheet(@PathVariable Long id){
        return new ResponseEntity(outSourceProcessSheetService.findById(id), HttpStatus.OK);
    }

    @Log("查看委外加工单产品信息")
    @ApiOperation(value = "查看委外加工单产品信息")
    @GetMapping(value = "/queryOutSourceProcessSheetProductList")
    @PreAuthorize("hasAnyRole('ADMIN','OUT_SOURCE_PROCESS_SHEET_ALL','OUT_SOURCE_PROCESS_SHEET_SELECT')")
    public ResponseEntity queryOutSourceProcessSheetProductList(OutSourceProcessSheetProductQueryCriteria criteria){
        return new ResponseEntity(outSourceProcessSheetProductService.queryAll(criteria), HttpStatus.OK);
    }
}