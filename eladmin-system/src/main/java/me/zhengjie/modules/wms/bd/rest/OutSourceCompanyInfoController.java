package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.bd.domain.OutSourceCompanyInfo;
import me.zhengjie.modules.wms.bd.service.OutSourceCompanyInfoService;
import me.zhengjie.modules.wms.bd.service.dto.OutSourceCompanyInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.OutSourceCompanyInfoQueryCriteria;
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
* @date 2019-08-03
*/
@Api(tags = "委外公司管理")
@RestController
@RequestMapping("api")
public class OutSourceCompanyInfoController {

    @Autowired
    private OutSourceCompanyInfoService outSourceCompanyInfoService;

    @Log("分页查询委外公司资料列表")
    @ApiOperation(value = "分页查询委外公司资料列表")
    @GetMapping(value = "/outSourceCompanyInfos")
    @PreAuthorize("hasAnyRole('ADMIN','BDOUTSOURCECOMPANYINFO_ALL','BDOUTSOURCECOMPANYINFO_SELECT')")
    public ResponseEntity getOutSourceCompanyInfos(OutSourceCompanyInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(outSourceCompanyInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }


    @Log("查询委外公司资料列表")
    @ApiOperation(value = "分页查询委外公司资料列表")
    @GetMapping(value = "/getOutSourceCompanyInfoList")
    @PreAuthorize("hasAnyRole('ADMIN','BDOUTSOURCECOMPANYINFO_ALL','BDOUTSOURCECOMPANYINFO_SELECT')")
    public ResponseEntity getOutSourceCompanyInfoList(OutSourceCompanyInfoQueryCriteria criteria){
        return new ResponseEntity(outSourceCompanyInfoService.queryAll(criteria),HttpStatus.OK);
    }


    @Log("查询委外公司详情")
    @ApiOperation(value = "查询委外公司详情")
    @GetMapping(value = "/outSourceCompanyInfo/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','BDOUTSOURCECOMPANYINFO_ALL','BDOUTSOURCECOMPANYINFO_SELECT')")
    public ResponseEntity getOutSourceCompanyInfoList(@PathVariable("id") Long id){
        OutSourceCompanyInfoDTO outSourceCompanyInfoDTO = outSourceCompanyInfoService.findById(id);
        return new ResponseEntity(outSourceCompanyInfoDTO,HttpStatus.OK);
    }


    @Log("新增委外公司资料")
    @ApiOperation(value = "新增委外公司资料")
    @PostMapping(value = "/outSourceCompanyInfo")
    @PreAuthorize("hasAnyRole('ADMIN','BDOUTSOURCECOMPANYINFO_ALL','BDOUTSOURCECOMPANYINFO_CREATE')")
    public ResponseEntity create(@Validated @RequestBody OutSourceCompanyInfo resources){
        return new ResponseEntity(outSourceCompanyInfoService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改委外公司资料")
    @ApiOperation(value = "修改委外公司资料")
    @PutMapping(value = "/outSourceCompanyInfo")
    @PreAuthorize("hasAnyRole('ADMIN','BDOUTSOURCECOMPANYINFO_ALL','BDOUTSOURCECOMPANYINFO_EDIT')")
    public ResponseEntity update(@Validated @RequestBody OutSourceCompanyInfo resources){
        outSourceCompanyInfoService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除委外公司资料")
    @ApiOperation(value = "删除委外公司资料")
    @DeleteMapping(value = "/outSourceCompanyInfo/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','BDOUTSOURCECOMPANYINFO_ALL','BDOUTSOURCECOMPANYINFO_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        outSourceCompanyInfoService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}