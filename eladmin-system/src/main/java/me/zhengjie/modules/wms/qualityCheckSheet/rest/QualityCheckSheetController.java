package me.zhengjie.modules.wms.qualityCheckSheet.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductQueryCriteria;
import me.zhengjie.modules.wms.qualityCheckSheet.request.CreateQualityCheckSheetRequest;
import me.zhengjie.modules.wms.qualityCheckSheet.request.UpdateQualityCheckSheetRequest;
import me.zhengjie.modules.wms.qualityCheckSheet.service.QualityCheckSheetProductService;
import me.zhengjie.modules.wms.qualityCheckSheet.service.QualityCheckSheetService;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetProductQueryCriteria;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
* @author huangxingxing
* @date 2019-11-12
*/
@Api(tags = "质量检验单管理")
@RestController
@RequestMapping("api")
public class QualityCheckSheetController {

    @Autowired
    private QualityCheckSheetService qualityCheckSheetService;

    @Autowired
    private QualityCheckSheetProductService qualityCheckSheetProductService;

    @Log("分页查询质量管理检验单")
    @ApiOperation(value = "分页查询质量检验单")
    @GetMapping(value = "/queryQualityCheckSheetPage")
//    @PreAuthorize("hasAnyRole('ADMIN','QUALITYCHECKSHEET_ALL','QUALITYCHECKSHEET_SELECT')")
    public ResponseEntity queryQualityCheckSheetPage(QualityCheckSheetQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(qualityCheckSheetService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增质量检验单")
    @ApiOperation(value = "新增质量检验单")
    @PostMapping(value = "/qualityCheckSheet")
//    @PreAuthorize("hasAnyRole('ADMIN','QUALITYCHECKSHEET_ALL','QUALITYCHECKSHEET_CREATE')")
    public ResponseEntity create(@Validated @RequestBody CreateQualityCheckSheetRequest createQualityCheckSheetRequest){
        return new ResponseEntity(qualityCheckSheetService.create(createQualityCheckSheetRequest),HttpStatus.CREATED);
    }

    @Log("修改质量检验单")
    @ApiOperation(value = "修改质量检验单")
    @PutMapping(value = "/qualityCheckSheet")
//    @PreAuthorize("hasAnyRole('ADMIN','QUALITYCHECKSHEET_ALL','QUALITYCHECKSHEET_EDIT')")
    public ResponseEntity update(@Validated @RequestBody UpdateQualityCheckSheetRequest updateQualityCheckSheetRequest){
        qualityCheckSheetService.update(updateQualityCheckSheetRequest);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除质量检验单")
    @ApiOperation(value = "删除质量检验单")
    @DeleteMapping(value = "/qualityCheckSheet/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN','QUALITYCHECKSHEET_ALL','QUALITYCHECKSHEET_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        qualityCheckSheetService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }


    @Log("初始化质量检验单单据编号")
    @ApiOperation(value = "初始化质量检验单单据编号")
    @GetMapping(value = "/initQualityCheckSheetCode")
    public ResponseEntity initQualityCheckSheetCode(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");//设置日期格式
        String supplierCode = "QCS"+ LocalDateTime.now().format(fmt);
        return new ResponseEntity(supplierCode,HttpStatus.OK);
    }


    @Log("查看质量检验单")
    @ApiOperation(value = "查看委外加工单")
    @GetMapping(value = "/qualityCheckSheet/{id}")
    public ResponseEntity getQualityCheckSheet(@PathVariable Long id){
        return new ResponseEntity(qualityCheckSheetService.findById(id), HttpStatus.OK);
    }

    @Log("查看委外加工单产品信息")
    @ApiOperation(value = "查看委外加工单产品信息")
    @GetMapping(value = "/qualityCheckSheetProductList")
    public ResponseEntity queryQualityCheckSheetProductList(QualityCheckSheetProductQueryCriteria criteria){
        return new ResponseEntity(qualityCheckSheetProductService.queryAll(criteria), HttpStatus.OK);
    }
}