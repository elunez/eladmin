package me.zhengjie.modules.wms.order.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.order.domain.CustomerOrderProduct;
import me.zhengjie.modules.wms.order.service.CustomerOrderProductService;
import me.zhengjie.modules.wms.order.service.dto.CustomerOrderProductQueryCriteria;
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
@Api(tags = "SCustomerOrderProduct管理")
@RestController
@RequestMapping("api")
public class CustomerOrderProductController {

    @Autowired
    private CustomerOrderProductService customerOrderProductService;

    @Log("查询SCustomerOrderProduct")
    @ApiOperation(value = "查询SCustomerOrderProduct")
    @GetMapping(value = "/sCustomerOrderProduct")
    @PreAuthorize("hasAnyRole('ADMIN','SCUSTOMERORDERPRODUCT_ALL','SCUSTOMERORDERPRODUCT_SELECT')")
    public ResponseEntity getSCustomerOrderProducts(CustomerOrderProductQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(customerOrderProductService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增SCustomerOrderProduct")
    @ApiOperation(value = "新增SCustomerOrderProduct")
    @PostMapping(value = "/sCustomerOrderProduct")
    @PreAuthorize("hasAnyRole('ADMIN','SCUSTOMERORDERPRODUCT_ALL','SCUSTOMERORDERPRODUCT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody CustomerOrderProduct resources){
        return new ResponseEntity(customerOrderProductService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改SCustomerOrderProduct")
    @ApiOperation(value = "修改SCustomerOrderProduct")
    @PutMapping(value = "/sCustomerOrderProduct")
    @PreAuthorize("hasAnyRole('ADMIN','SCUSTOMERORDERPRODUCT_ALL','SCUSTOMERORDERPRODUCT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody CustomerOrderProduct resources){
        customerOrderProductService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除SCustomerOrderProduct")
    @ApiOperation(value = "删除SCustomerOrderProduct")
    @DeleteMapping(value = "/sCustomerOrderProduct/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SCUSTOMERORDERPRODUCT_ALL','SCUSTOMERORDERPRODUCT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        customerOrderProductService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}