package me.zhengjie.modules.wms.invoice.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.invoice.domain.Invoice;
import me.zhengjie.modules.wms.invoice.request.CreateInvoiceRequest;
import me.zhengjie.modules.wms.invoice.request.UpdateInvoiceRequest;
import me.zhengjie.modules.wms.invoice.service.InvoiceService;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceQueryCriteria;
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
* @date 2019-08-27
*/
@Api(tags = "发货单管理")
@RestController
@RequestMapping("api")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Log("分页查询销售发货单")
    @ApiOperation(value = "分页查询销售发货单")
    @GetMapping(value = "/queryInvoicePage")
    @PreAuthorize("hasAnyRole('ADMIN','SINVOICE_ALL','SINVOICE_SELECT')")
    public ResponseEntity queryInvoicePage(InvoiceQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(invoiceService.queryAll(criteria,pageable),HttpStatus.OK);
    }


    @Log("查询销售发货单列表")
    @ApiOperation(value = "查询销售发货单列表")
    @GetMapping(value = "/queryInvoiceList")
    @PreAuthorize("hasAnyRole('ADMIN','SINVOICE_ALL','SINVOICE_SELECT')")
    public ResponseEntity queryInvoiceList(InvoiceQueryCriteria criteria){
        return new ResponseEntity(invoiceService.queryAll(criteria),HttpStatus.OK);
    }

    @Log("新增销售发货单")
    @ApiOperation(value = "新增销售发货单")
    @PostMapping(value = "/invoice")
    @PreAuthorize("hasAnyRole('ADMIN','SINVOICE_ALL','SINVOICE_CREATE')")
    public ResponseEntity create(@RequestBody CreateInvoiceRequest createInvoiceRequest){
        return new ResponseEntity(invoiceService.create(createInvoiceRequest),HttpStatus.CREATED);
    }

    @Log("修改销售发货单")
    @ApiOperation(value = "修改销售发货单")
    @PutMapping(value = "/invoice")
    @PreAuthorize("hasAnyRole('ADMIN','SINVOICE_ALL','SINVOICE_EDIT')")
    public ResponseEntity update(@RequestBody UpdateInvoiceRequest updateInvoiceRequest){
        invoiceService.update(updateInvoiceRequest);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除销售发货单")
    @ApiOperation(value = "删除销售发货单")
    @DeleteMapping(value = "/sInvoice/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SINVOICE_ALL','SINVOICE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        invoiceService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("初始化发货单编号")
    @GetMapping(value = "/initInvoiceCode")
    public ResponseEntity initInvoiceCode(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");//设置日期格式
        String supplierCode = "INVOICE"+ LocalDateTime.now().format(fmt);
        return new ResponseEntity(supplierCode,HttpStatus.OK);
    }

    @Log("查看发货单详情")
    @GetMapping(value = "/invoice/{id}")
    public ResponseEntity getInvoiceInfo(@PathVariable Long id){
        return new ResponseEntity(invoiceService.findById(id), HttpStatus.OK);
    }
}