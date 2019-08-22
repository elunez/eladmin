package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.bd.domain.ProductInfo;
import me.zhengjie.modules.wms.bd.request.CreateProductInfoRequest;
import me.zhengjie.modules.wms.bd.service.ProductInfoService;
import me.zhengjie.modules.wms.bd.service.dto.ProductInfoQueryCriteria;
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
* @author 黄星星
* @date 2019-07-27
*/
@Api(tags = "产品资料管理")
@RestController
@RequestMapping("api")
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;



    @Log("初始化产品编号")
    @ApiOperation(value = "初始化产品编号")
    @GetMapping(value = "/initProductInfoCode")
    @PreAuthorize("hasAnyRole('ADMIN','BDSUPPLIERINFO_ALL','BDSUPPLIERINFO_SELECT')")
    public ResponseEntity initProductInfoCode(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");//设置日期格式
        String supplierCode = "CP"+ LocalDateTime.now().format(fmt);
        return new ResponseEntity(supplierCode,HttpStatus.OK);
    }


    @Log("分页查询产品资料")
    @ApiOperation(value = "分页查询产品资料")
    @GetMapping(value = "/productInfo")
    @PreAuthorize("hasAnyRole('ADMIN','BDPRODUCTINFO_ALL','BDPRODUCTINFO_SELECT')")
    public ResponseEntity getProductInfos(ProductInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(productInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("查询产品资料列表")
    @ApiOperation(value = "查询产品资料列表")
    @GetMapping(value = "/productInfo/all")
    public ResponseEntity queryProductInfoList(ProductInfoQueryCriteria criteria){
        return new ResponseEntity(productInfoService.queryAll(criteria),HttpStatus.OK);
    }

    @Log("新增产品资料")
    @ApiOperation(value = "新增产品资料")
    @PostMapping(value = "/productInfo")
    @PreAuthorize("hasAnyRole('ADMIN','BDPRODUCTINFO_ALL','BDPRODUCTINFO_CREATE')")
    public ResponseEntity create(@RequestBody CreateProductInfoRequest createProductInfoRequest){
        return new ResponseEntity(productInfoService.create(createProductInfoRequest),HttpStatus.CREATED);
    }

    @Log("修改产品资料")
    @ApiOperation(value = "修改产品资料")
    @PutMapping(value = "/productInfo")
    @PreAuthorize("hasAnyRole('ADMIN','BDPRODUCTINFO_ALL','BDPRODUCTINFO_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ProductInfo resources){
        productInfoService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除产品资料")
    @ApiOperation(value = "删除产品资料")
    @DeleteMapping(value = "/productInfo/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','BDPRODUCTINFO_ALL','BDPRODUCTINFO_DELETE')")
    public ResponseEntity delete(@PathVariable Integer id){
        productInfoService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查看产品资料详情")
    @GetMapping(value = "/productInfo/{id}")
    public ResponseEntity getMessureUnit(@PathVariable Long id){
        return new ResponseEntity(productInfoService.findById(id), HttpStatus.OK);
    }

}