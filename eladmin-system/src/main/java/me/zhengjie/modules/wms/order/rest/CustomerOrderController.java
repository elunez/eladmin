package me.zhengjie.modules.wms.order.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.order.domain.CustomerOrder;
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

/**
* @author jie
* @date 2019-08-03
*/
@Api(tags = "SCustomerOrder管理")
@RestController
@RequestMapping("api")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderService customerOrderService;

    @Log("分页查询客户订单列表")
    @ApiOperation(value = "分页查询客户订单列表")
    @GetMapping(value = "/customerOrders")
    @PreAuthorize("hasAnyRole('ADMIN','SCUSTOMERORDER_ALL','SCUSTOMERORDER_SELECT')")
    public ResponseEntity getCustomerOrders(CustomerOrderQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(customerOrderService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增客户订单")
    @ApiOperation(value = "新增客户订单")
    @PostMapping(value = "/customerOrder")
    @PreAuthorize("hasAnyRole('ADMIN','SCUSTOMERORDER_ALL','SCUSTOMERORDER_CREATE')")
    public ResponseEntity create(@Validated @RequestBody CustomerOrder resources){
        return new ResponseEntity(customerOrderService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改SCustomerOrder")
    @ApiOperation(value = "修改SCustomerOrder")
    @PutMapping(value = "/customerOrder")
    @PreAuthorize("hasAnyRole('ADMIN','SCUSTOMERORDER_ALL','SCUSTOMERORDER_EDIT')")
    public ResponseEntity update(@Validated @RequestBody CustomerOrder resources){
        customerOrderService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除SCustomerOrder")
    @ApiOperation(value = "删除SCustomerOrder")
    @DeleteMapping(value = "/customerOrder/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SCUSTOMERORDER_ALL','SCUSTOMERORDER_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        customerOrderService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}