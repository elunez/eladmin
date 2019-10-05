package me.zhengjie.modules.wms.purchase.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.purchase.domain.ConsumablesPurchaseOrderProduct;
import me.zhengjie.modules.wms.purchase.service.ConsumablesPurchaseOrderProductService;
import me.zhengjie.modules.wms.purchase.service.dto.ConsumablesPurchaseOrderProductQueryCriteria;
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
@Api(tags = "ConsumablesPurchaseOrderProduct管理")
@RestController
@RequestMapping("api")
public class ConsumablesPurchaseOrderProductController {

    @Autowired
    private ConsumablesPurchaseOrderProductService consumablesPurchaseOrderProductService;

    @Log("查询ConsumablesPurchaseOrderProduct")
    @ApiOperation(value = "查询ConsumablesPurchaseOrderProduct")
    @GetMapping(value = "/consumablesPurchaseOrderProduct")
    @PreAuthorize("hasAnyRole('ADMIN','CONSUMABLESPURCHASEORDERPRODUCT_ALL','CONSUMABLESPURCHASEORDERPRODUCT_SELECT')")
    public ResponseEntity getConsumablesPurchaseOrderProducts(ConsumablesPurchaseOrderProductQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(consumablesPurchaseOrderProductService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增ConsumablesPurchaseOrderProduct")
    @ApiOperation(value = "新增ConsumablesPurchaseOrderProduct")
    @PostMapping(value = "/consumablesPurchaseOrderProduct")
    @PreAuthorize("hasAnyRole('ADMIN','CONSUMABLESPURCHASEORDERPRODUCT_ALL','CONSUMABLESPURCHASEORDERPRODUCT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ConsumablesPurchaseOrderProduct resources){
        return new ResponseEntity(consumablesPurchaseOrderProductService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改ConsumablesPurchaseOrderProduct")
    @ApiOperation(value = "修改ConsumablesPurchaseOrderProduct")
    @PutMapping(value = "/consumablesPurchaseOrderProduct")
    @PreAuthorize("hasAnyRole('ADMIN','CONSUMABLESPURCHASEORDERPRODUCT_ALL','CONSUMABLESPURCHASEORDERPRODUCT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ConsumablesPurchaseOrderProduct resources){
        consumablesPurchaseOrderProductService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除ConsumablesPurchaseOrderProduct")
    @ApiOperation(value = "删除ConsumablesPurchaseOrderProduct")
    @DeleteMapping(value = "/consumablesPurchaseOrderProduct/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CONSUMABLESPURCHASEORDERPRODUCT_ALL','CONSUMABLESPURCHASEORDERPRODUCT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        consumablesPurchaseOrderProductService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}