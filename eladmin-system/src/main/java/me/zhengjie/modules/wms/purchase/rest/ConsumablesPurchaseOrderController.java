package me.zhengjie.modules.wms.purchase.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.purchase.domain.ConsumablesPurchaseOrder;
import me.zhengjie.modules.wms.purchase.request.AuditConsumablesPurchaseOrderRequest;
import me.zhengjie.modules.wms.purchase.request.AuditProductPurchaseOrderRequest;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
* @author jie
* @date 2019-10-06
*/
@Api(tags = "耗材采购管理")
@RestController
@RequestMapping("api")
public class ConsumablesPurchaseOrderController {

    @Autowired
    private ConsumablesPurchaseOrderService consumablesPurchaseOrderService;

    @Log("分页查询耗材采购单")
    @ApiOperation(value = "分页查询耗材采购单")
    @GetMapping(value = "/queryConsumablesPurchaseOrderPageList")
    @PreAuthorize("hasAnyRole('ADMIN','CONSUMABLESPURCHASEORDER_ALL','CONSUMABLESPURCHASEORDER_SELECT')")
    public ResponseEntity queryConsumablesPurchaseOrderPageList(ConsumablesPurchaseOrderQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(consumablesPurchaseOrderService.queryAll(criteria,pageable),HttpStatus.OK);
    }


    @Log("查询耗材采购单列表")
    @ApiOperation(value = "查询耗材采购单列表")
    @GetMapping(value = "/queryConsumablesPurchaseOrderList")
    @PreAuthorize("hasAnyRole('ADMIN','CONSUMABLESPURCHASEORDER_ALL','CONSUMABLESPURCHASEORDER_SELECT')")
    public ResponseEntity queryConsumablesPurchaseOrderList(ConsumablesPurchaseOrderQueryCriteria criteria){
        return new ResponseEntity(consumablesPurchaseOrderService.queryAll(criteria),HttpStatus.OK);
    }

    @Log("新增耗材采购单")
    @ApiOperation(value = "新增耗材采购单")
    @PostMapping(value = "/consumablesPurchaseOrder")
    @PreAuthorize("hasAnyRole('ADMIN','CONSUMABLESPURCHASEORDER_ALL','CONSUMABLESPURCHASEORDER_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ConsumablesPurchaseOrder resources){
        return new ResponseEntity(consumablesPurchaseOrderService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改耗材采购单")
    @ApiOperation(value = "修改耗材采购单")
    @PutMapping(value = "/consumablesPurchaseOrder")
    @PreAuthorize("hasAnyRole('ADMIN','CONSUMABLESPURCHASEORDER_ALL','CONSUMABLESPURCHASEORDER_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ConsumablesPurchaseOrder resources){
        consumablesPurchaseOrderService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除耗材采购单")
    @ApiOperation(value = "删除耗材采购单")
    @DeleteMapping(value = "/consumablesPurchaseOrder/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSUMABLESPURCHASEORDER_ALL','CONSUMABLESPURCHASEORDER_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        consumablesPurchaseOrderService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("审核耗材采购单")
    @ApiOperation(value = "审核耗材采购单")
    @PostMapping(value = "/auditConsumablesPurchaseOrder")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTPURCHASEORDER_ALL','PRODUCTPURCHASEORDER_DELETE')")
    public ResponseEntity auditConsumablesPurchaseOrder(@Validated @RequestBody AuditConsumablesPurchaseOrderRequest auditConsumablesPurchaseOrderRequest){
        consumablesPurchaseOrderService.auditConsumablesPurchaseOrder(auditConsumablesPurchaseOrderRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("初始化耗材采购单编号")
    @GetMapping(value = "/initConsumablesPurchaseOrderCode")
    public ResponseEntity initConsumablesPurchaseOrderCode(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");//设置日期格式
        String supplierCode = "CP"+ LocalDateTime.now().format(fmt);
        return new ResponseEntity(supplierCode,HttpStatus.OK);
    }
}