package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.bd.domain.CustomerInfo;
import me.zhengjie.modules.wms.bd.request.CreateCustomerInfoRequest;
import me.zhengjie.modules.wms.bd.request.UpdateCustomerInfoRequest;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Log("初始化客户编号")
    @ApiOperation(value = "初始化客户编号")
    @GetMapping(value = "/initCustomerCode")
    @PreAuthorize("hasAnyRole('ADMIN','BDSUPPLIERINFO_ALL','BDSUPPLIERINFO_SELECT')")
    public ResponseEntity initCustomerCode(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");//设置日期格式
        String supplierCode = "KH"+ LocalDateTime.now().format(fmt);
        return new ResponseEntity(supplierCode,HttpStatus.OK);
    }

    @Log("分页查询客户信息")
    @ApiOperation(value = "分页查询客户信息")
    @GetMapping(value = "/queryCustomerInfoPage")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER_INFO_ALL','CUSTOMER_INFO_SELECT')")
    public ResponseEntity queryCustomerInfoPage(CustomerInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(customerInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("查询客户信息列表")
    @ApiOperation(value = "查询客户信息列表")
    @GetMapping(value = "/queryCustomerInfoList")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER_INFO_ALL','CUSTOMER_INFO_SELECT')")
    public ResponseEntity queryCustomerInfoList(CustomerInfoQueryCriteria criteria){
        return new ResponseEntity(customerInfoService.queryAll(criteria),HttpStatus.OK);
    }

    @Log("查看客户信息资料详情")
    @GetMapping(value = "/customerInfo/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER_INFO_ALL','BDCUSTOMERINFO_DETAIL_BY_ID')")
    public ResponseEntity getCustomerInfo(@PathVariable Long id){
        return new ResponseEntity(customerInfoService.findById(id), HttpStatus.OK);
    }

    @Log("新增客户信息")
    @ApiOperation(value = "新增客户信息")
    @PostMapping(value = "/customerInfo")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER_INFO_ALL','CUSTOMER_INFO_CREATE')")
    public ResponseEntity create(@RequestBody CreateCustomerInfoRequest createCustomerInfoRequest){
        return new ResponseEntity(customerInfoService.create(createCustomerInfoRequest),HttpStatus.CREATED);
    }

    @Log("修改客户信息")
    @ApiOperation(value = "修改客户信息")
    @PutMapping(value = "/customerInfo/update")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER_INFO_ALL','CUSTOMER_INFO_EDIT')")
    public ResponseEntity update(@RequestBody UpdateCustomerInfoRequest updateCustomerInfoRequest){
        customerInfoService.update(updateCustomerInfoRequest);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除客户信息")
    @ApiOperation(value = "删除客户信息")
    @DeleteMapping(value = "/customerInfo/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER_INFO_ALL','CUSTOMER_INFO_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        customerInfoService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}