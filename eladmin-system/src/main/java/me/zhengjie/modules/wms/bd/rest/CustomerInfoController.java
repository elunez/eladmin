package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.bd.domain.CustomerInfo;
import me.zhengjie.modules.wms.bd.service.CustomerInfoService;
import me.zhengjie.modules.wms.bd.service.dto.CustomerInfoQueryCriteria;
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
@Api(tags = "客户信息管理")
@RestController
@RequestMapping("api")
public class CustomerInfoController {

    @Autowired
    private CustomerInfoService customerInfoService;

    @Log("分页查询客户信息")
    @ApiOperation(value = "分页查询客户信息")
    @GetMapping(value = "/customerInfos")
    @PreAuthorize("hasAnyRole('ADMIN','BDCUSTOMERINFO_ALL','BDCUSTOMERINFO_SELECT')")
    public ResponseEntity getCustomerInfos(CustomerInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(customerInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("查询客户信息列表")
    @ApiOperation(value = "查询客户信息列表")
    @GetMapping(value = "/customerInfo/queryCustomerInfoList")
    @PreAuthorize("hasAnyRole('ADMIN','BDCUSTOMERINFO_ALL','BDCUSTOMERINFO_SELECT')")
    public ResponseEntity queryCustomerInfoList(CustomerInfoQueryCriteria criteria){
        return new ResponseEntity(customerInfoService.queryAll(criteria),HttpStatus.OK);
    }

    @Log("查看客户信息资料详情")
    @GetMapping(value = "/customerInfo/{id}")
    public ResponseEntity getCustomerInfo(@PathVariable Long id){
        return new ResponseEntity(customerInfoService.findById(id), HttpStatus.OK);
    }

    @Log("新增客户信息")
    @ApiOperation(value = "新增客户信息")
    @PostMapping(value = "/customerInfo")
    @PreAuthorize("hasAnyRole('ADMIN','BDCUSTOMERINFO_ALL','BDCUSTOMERINFO_CREATE')")
    public ResponseEntity create(@Validated @RequestBody CustomerInfo resources){
        return new ResponseEntity(customerInfoService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改客户信息")
    @ApiOperation(value = "修改客户信息")
    @PutMapping(value = "/customerInfo/update")
    @PreAuthorize("hasAnyRole('ADMIN','BDCUSTOMERINFO_ALL','BDCUSTOMERINFO_EDIT')")
    public ResponseEntity update(@Validated @RequestBody CustomerInfo resources){
        customerInfoService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除客户信息")
    @ApiOperation(value = "删除客户信息")
    @DeleteMapping(value = "/customerInfo/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','BDCUSTOMERINFO_ALL','BDCUSTOMERINFO_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        customerInfoService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}