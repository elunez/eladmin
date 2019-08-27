package me.zhengjie.modules.wms.invoice.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.invoice.domain.InvoiceProduct;
import me.zhengjie.modules.wms.invoice.service.InvoiceProductService;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceProductQueryCriteria;
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
* @date 2019-08-27
*/
@Api(tags = "SInvoiceProduct管理")
@RestController
@RequestMapping("api")
public class InvoiceProductController {

    @Autowired
    private InvoiceProductService invoiceProductService;

    @Log("查询SInvoiceProduct")
    @ApiOperation(value = "查询SInvoiceProduct")
    @GetMapping(value = "/sInvoiceProduct")
    @PreAuthorize("hasAnyRole('ADMIN','SINVOICEPRODUCT_ALL','SINVOICEPRODUCT_SELECT')")
    public ResponseEntity getSInvoiceProducts(InvoiceProductQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(invoiceProductService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增SInvoiceProduct")
    @ApiOperation(value = "新增SInvoiceProduct")
    @PostMapping(value = "/sInvoiceProduct")
    @PreAuthorize("hasAnyRole('ADMIN','SINVOICEPRODUCT_ALL','SINVOICEPRODUCT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody InvoiceProduct resources){
        return new ResponseEntity(invoiceProductService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改SInvoiceProduct")
    @ApiOperation(value = "修改SInvoiceProduct")
    @PutMapping(value = "/sInvoiceProduct")
    @PreAuthorize("hasAnyRole('ADMIN','SINVOICEPRODUCT_ALL','SINVOICEPRODUCT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody InvoiceProduct resources){
        invoiceProductService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除SInvoiceProduct")
    @ApiOperation(value = "删除SInvoiceProduct")
    @DeleteMapping(value = "/sInvoiceProduct/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINVOICEPRODUCT_ALL','SINVOICEPRODUCT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        invoiceProductService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}