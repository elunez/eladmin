package me.zhengjie.modules.wms.purchase.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrder;
import me.zhengjie.modules.wms.purchase.service.ProductPurchaseOrderService;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderQueryCriteria;
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
@Api(tags = "ProductPurchaseOrder管理")
@RestController
@RequestMapping("api")
public class ProductPurchaseOrderController {

    @Autowired
    private ProductPurchaseOrderService productPurchaseOrderService;

    @Log("查询ProductPurchaseOrder")
    @ApiOperation(value = "查询ProductPurchaseOrder")
    @GetMapping(value = "/productPurchaseOrder")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTPURCHASEORDER_ALL','PRODUCTPURCHASEORDER_SELECT')")
    public ResponseEntity getProductPurchaseOrders(ProductPurchaseOrderQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(productPurchaseOrderService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增ProductPurchaseOrder")
    @ApiOperation(value = "新增ProductPurchaseOrder")
    @PostMapping(value = "/productPurchaseOrder")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTPURCHASEORDER_ALL','PRODUCTPURCHASEORDER_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ProductPurchaseOrder resources){
        return new ResponseEntity(productPurchaseOrderService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改ProductPurchaseOrder")
    @ApiOperation(value = "修改ProductPurchaseOrder")
    @PutMapping(value = "/productPurchaseOrder")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTPURCHASEORDER_ALL','PRODUCTPURCHASEORDER_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ProductPurchaseOrder resources){
        productPurchaseOrderService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除ProductPurchaseOrder")
    @ApiOperation(value = "删除ProductPurchaseOrder")
    @DeleteMapping(value = "/productPurchaseOrder/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTPURCHASEORDER_ALL','PRODUCTPURCHASEORDER_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        productPurchaseOrderService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}