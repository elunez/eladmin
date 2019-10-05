package me.zhengjie.modules.wms.outSourceProductSheet.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificate;
import me.zhengjie.modules.wms.outSourceProductSheet.request.CreateOutSourceInspectionCertificateRequest;
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

    @Log("查询委外验收单")
    @ApiOperation(value = "查询委外验收单")
    @GetMapping(value = "/outSourceInspectionCertificate")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEINSPECTIONCERTIFICATE_ALL','SOUTSOURCEINSPECTIONCERTIFICATE_SELECT')")
    public ResponseEntity getOutSourceInspectionCertificates(OutSourceInspectionCertificateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(outSourceInspectionCertificateService.queryAll(criteria,pageable),HttpStatus.OK);
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
    public ResponseEntity update(@Validated @RequestBody OutSourceInspectionCertificate resources){
        outSourceInspectionCertificateService.update(resources);
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
    @GetMapping(value = "/outSourceInspectionCertificate/{id}")
    public ResponseEntity getOutSourceInspectionCertificate(@PathVariable Long id){
        return new ResponseEntity(outSourceInspectionCertificateService.findById(id), HttpStatus.OK);
    }

}