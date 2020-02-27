package me.zhengjie.modules.wms.outSourceProductSheet.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificate;
import me.zhengjie.modules.wms.outSourceProductSheet.request.CreateOutSourceInspectionCertificateRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.request.UpdateOutSourceInspectionCertificateRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.service.OutSourceInspectionCertificateService;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateQueryCriteria;
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
* @date 2019-10-01
*/
@Api(tags = "委外验收单管理")
@RestController
@RequestMapping("api")
public class OutSourceInspectionCertificateController {

    @Autowired
    private OutSourceInspectionCertificateService outSourceInspectionCertificateService;

    @Log("分页查询委外验收单")
    @ApiOperation(value = "分页查询委外验收单")
    @GetMapping(value = "/queryOutSourceInspectionCertificatePageList")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEINSPECTIONCERTIFICATE_ALL','SOUTSOURCEINSPECTIONCERTIFICATE_SELECT')")
    public ResponseEntity queryOutSourceInspectionCertificatePageList(OutSourceInspectionCertificateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(outSourceInspectionCertificateService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("查询委外验收单列表")
    @ApiOperation(value = "查询委外验收单列表")
    @GetMapping(value = "/queryOutSourceInspectionCertificateList")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEINSPECTIONCERTIFICATE_ALL','SOUTSOURCEINSPECTIONCERTIFICATE_SELECT')")
    public ResponseEntity queryOutSourceInspectionCertificateList(OutSourceInspectionCertificateQueryCriteria criteria){
        return new ResponseEntity(outSourceInspectionCertificateService.queryAll(criteria),HttpStatus.OK);
    }


    @Log("新增委外验收单")
    @ApiOperation(value = "新增委外验收单")
    @PostMapping(value = "/outSourceInspectionCertificate")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEINSPECTIONCERTIFICATE_ALL','SOUTSOURCEINSPECTIONCERTIFICATE_CREATE')")
    public ResponseEntity create(@Validated @RequestBody CreateOutSourceInspectionCertificateRequest createOutSourceInspectionCertificateRequest){
        return new ResponseEntity(outSourceInspectionCertificateService.create(createOutSourceInspectionCertificateRequest),HttpStatus.CREATED);
    }

    @Log("修改委外验收单")
    @ApiOperation(value = "修改委外验收单")
    @PutMapping(value = "/outSourceInspectionCertificate")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEINSPECTIONCERTIFICATE_ALL','SOUTSOURCEINSPECTIONCERTIFICATE_EDIT')")
    public ResponseEntity update(@Validated @RequestBody UpdateOutSourceInspectionCertificateRequest updateOutSourceInspectionCertificateRequest){
        outSourceInspectionCertificateService.update(updateOutSourceInspectionCertificateRequest);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除委外验收单")
    @ApiOperation(value = "删除委外验收单")
    @DeleteMapping(value = "/outSourceInspectionCertificate/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEINSPECTIONCERTIFICATE_ALL','SOUTSOURCEINSPECTIONCERTIFICATE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        outSourceInspectionCertificateService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查看委外验收单详情")
    @ApiOperation(value = "查看委外验收单详情")
    @GetMapping(value = "/outSourceInspectionCertificate/{id}")
    public ResponseEntity getOutSourceInspectionCertificate(@PathVariable Long id){
        return new ResponseEntity(outSourceInspectionCertificateService.findById(id), HttpStatus.OK);
    }

    @Log("初始化委外验收单编号")
    @ApiOperation(value = "初始化委外验收单编号")
    @GetMapping(value = "/initOutSourceInspectionCertificateCode")
    public ResponseEntity initOutSourceInspectionCertificateCode(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");//设置日期格式
        String supplierCode = "OIC"+ LocalDateTime.now().format(fmt);
        return new ResponseEntity(supplierCode,HttpStatus.OK);
    }

}