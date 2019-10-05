package me.zhengjie.modules.wms.purchase.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.purchase.domain.ConsumablesPurchaseOrder;
import me.zhengjie.modules.wms.purchase.service.ConsumablesPurchaseOrderService;
import me.zhengjie.modules.wms.purchase.service.dto.ConsumablesPurchaseOrderQueryCriteria;
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
* @date 2019-10-06
*/
@Api(tags = "ConsumablesPurchaseOrder管理")
@RestController
@RequestMapping("api")
public class ConsumablesPurchaseOrderController {

    @Autowired
    private ConsumablesPurchaseOrderService consumablesPurchaseOrderService;

    @Log("查询ConsumablesPurchaseOrder")
    @ApiOperation(value = "查询ConsumablesPurchaseOrder")
    @GetMapping(value = "/consumablesPurchaseOrder")
    @PreAuthorize("hasAnyRole('ADMIN','CONSUMABLESPURCHASEORDER_ALL','CONSUMABLESPURCHASEORDER_SELECT')")
    public ResponseEntity getConsumablesPurchaseOrders(ConsumablesPurchaseOrderQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(consumablesPurchaseOrderService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增ConsumablesPurchaseOrder")
    @ApiOperation(value = "新增ConsumablesPurchaseOrder")
    @PostMapping(value = "/consumablesPurchaseOrder")
    @PreAuthorize("hasAnyRole('ADMIN','CONSUMABLESPURCHASEORDER_ALL','CONSUMABLESPURCHASEORDER_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ConsumablesPurchaseOrder resources){
        return new ResponseEntity(consumablesPurchaseOrderService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改ConsumablesPurchaseOrder")
    @ApiOperation(value = "修改ConsumablesPurchaseOrder")
    @PutMapping(value = "/consumablesPurchaseOrder")
    @PreAuthorize("hasAnyRole('ADMIN','CONSUMABLESPURCHASEORDER_ALL','CONSUMABLESPURCHASEORDER_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ConsumablesPurchaseOrder resources){
        consumablesPurchaseOrderService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除ConsumablesPurchaseOrder")
    @ApiOperation(value = "删除ConsumablesPurchaseOrder")
    @DeleteMapping(value = "/consumablesPurchaseOrder/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSUMABLESPURCHASEORDER_ALL','CONSUMABLESPURCHASEORDER_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        consumablesPurchaseOrderService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}