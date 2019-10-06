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
 * 产品采购管理控制类
* @author jie
* @date 2019-10-06
*/
@Api(tags = "产品采购管理")
@RestController
@RequestMapping("api")
public class ProductPurchaseOrderController {

    @Autowired
    private ProductPurchaseOrderService productPurchaseOrderService;

    @Log("分页查询产品采购单")
    @ApiOperation(value = "分页查询产品采购单")
    @GetMapping(value = "/queryProductPurchaseOrderPageList")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTPURCHASEORDER_ALL','PRODUCTPURCHASEORDER_SELECT')")
    public ResponseEntity queryProductPurchaseOrderPageList(ProductPurchaseOrderQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(productPurchaseOrderService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增产品采购单")
    @ApiOperation(value = "新增产品采购单")
    @PostMapping(value = "/productPurchaseOrder")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTPURCHASEORDER_ALL','PRODUCTPURCHASEORDER_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ProductPurchaseOrder resources){
        return new ResponseEntity(productPurchaseOrderService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改产品采购")
    @ApiOperation(value = "修改产品采购")
    @PutMapping(value = "/productPurchaseOrder")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTPURCHASEORDER_ALL','PRODUCTPURCHASEORDER_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ProductPurchaseOrder resources){
        productPurchaseOrderService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除产品采购")
    @ApiOperation(value = "删除产品采购")
    @DeleteMapping(value = "/productPurchaseOrder/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTPURCHASEORDER_ALL','PRODUCTPURCHASEORDER_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        productPurchaseOrderService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}