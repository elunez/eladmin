package me.zhengjie.modules.wms.order.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.order.domain.CustomerOrder;
import me.zhengjie.modules.wms.order.request.CreateCustomerOrderRequest;
import me.zhengjie.modules.wms.order.service.CustomerOrderService;
import me.zhengjie.modules.wms.order.service.dto.CustomerOrderQueryCriteria;
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
@Api(tags = "客户订单管理")
@RestController
@RequestMapping("api")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderService customerOrderService;

    @Log("分页查询客户订单列表")
    @ApiOperation(value = "分页查询客户订单列表")
    @GetMapping(value = "/queryCustomerOrderPage")
    @PreAuthorize("hasAnyRole('ADMIN','SCUSTOMERORDER_ALL','SCUSTOMERORDER_SELECT')")
    public ResponseEntity queryCustomerOrderPage(CustomerOrderQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(customerOrderService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增客户订单")
    @ApiOperation(value = "新增客户订单")
    @PostMapping(value = "/customerOrder")
    @PreAuthorize("hasAnyRole('ADMIN','SCUSTOMERORDER_ALL','SCUSTOMERORDER_CREATE')")
    public ResponseEntity create(@RequestBody CreateCustomerOrderRequest createCustomerOrderRequest){
        return new ResponseEntity(customerOrderService.create(createCustomerOrderRequest),HttpStatus.CREATED);
    }

    @Log("修改客户订单")
    @ApiOperation(value = "修改SCustomerOrder")
    @PutMapping(value = "/customerOrder")
    @PreAuthorize("hasAnyRole('ADMIN','SCUSTOMERORDER_ALL','SCUSTOMERORDER_EDIT')")
    public ResponseEntity update(@RequestBody CustomerOrder resources){
        customerOrderService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除客户订单")
    @ApiOperation(value = "删除SCustomerOrder")
    @DeleteMapping(value = "/customerOrder/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SCUSTOMERORDER_ALL','SCUSTOMERORDER_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        customerOrderService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("初始化客户订单编号")
    @GetMapping(value = "/initCustomerOrderCode")
    public ResponseEntity initCustomerOrderCode(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");//设置日期格式
        String supplierCode = "DD"+ LocalDateTime.now().format(fmt);
        return new ResponseEntity(supplierCode,HttpStatus.OK);
    }
}