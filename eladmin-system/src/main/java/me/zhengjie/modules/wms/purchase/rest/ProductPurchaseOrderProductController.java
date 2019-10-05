package me.zhengjie.modules.wms.purchase.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrderProduct;
import me.zhengjie.modules.wms.purchase.service.ProductPurchaseOrderProductService;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderProductQueryCriteria;
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
@Api(tags = "ProductPurchaseOrderProduct管理")
@RestController
@RequestMapping("api")
public class ProductPurchaseOrderProductController {

    @Autowired
    private ProductPurchaseOrderProductService productPurchaseOrderProductService;

    @Log("查询ProductPurchaseOrderProduct")
    @ApiOperation(value = "查询ProductPurchaseOrderProduct")
    @GetMapping(value = "/productPurchaseOrderProduct")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTPURCHASEORDERPRODUCT_ALL','PRODUCTPURCHASEORDERPRODUCT_SELECT')")
    public ResponseEntity getProductPurchaseOrderProducts(ProductPurchaseOrderProductQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(productPurchaseOrderProductService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增ProductPurchaseOrderProduct")
    @ApiOperation(value = "新增ProductPurchaseOrderProduct")
    @PostMapping(value = "/productPurchaseOrderProduct")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTPURCHASEORDERPRODUCT_ALL','PRODUCTPURCHASEORDERPRODUCT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ProductPurchaseOrderProduct resources){
        return new ResponseEntity(productPurchaseOrderProductService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改ProductPurchaseOrderProduct")
    @ApiOperation(value = "修改ProductPurchaseOrderProduct")
    @PutMapping(value = "/productPurchaseOrderProduct")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTPURCHASEORDERPRODUCT_ALL','PRODUCTPURCHASEORDERPRODUCT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ProductPurchaseOrderProduct resources){
        productPurchaseOrderProductService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除ProductPurchaseOrderProduct")
    @ApiOperation(value = "删除ProductPurchaseOrderProduct")
    @DeleteMapping(value = "/productPurchaseOrderProduct/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTPURCHASEORDERPRODUCT_ALL','PRODUCTPURCHASEORDERPRODUCT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        productPurchaseOrderProductService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}