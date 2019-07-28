package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.bd.domain.ProductInfo;
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

/**
* @author 黄星星
* @date 2019-07-27
*/
@Api(tags = "BdProductInfo管理")
@RestController
@RequestMapping("api")
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;

    @Log("查询BdProductInfo")
    @ApiOperation(value = "查询BdProductInfo")
    @GetMapping(value = "/bdProductInfo")
    @PreAuthorize("hasAnyRole('ADMIN','BDPRODUCTINFO_ALL','BDPRODUCTINFO_SELECT')")
    public ResponseEntity getBdProductInfos(ProductInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(productInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增BdProductInfo")
    @ApiOperation(value = "新增BdProductInfo")
    @PostMapping(value = "/bdProductInfo")
    @PreAuthorize("hasAnyRole('ADMIN','BDPRODUCTINFO_ALL','BDPRODUCTINFO_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ProductInfo resources){
        return new ResponseEntity(productInfoService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改BdProductInfo")
    @ApiOperation(value = "修改BdProductInfo")
    @PutMapping(value = "/bdProductInfo")
    @PreAuthorize("hasAnyRole('ADMIN','BDPRODUCTINFO_ALL','BDPRODUCTINFO_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ProductInfo resources){
        productInfoService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除BdProductInfo")
    @ApiOperation(value = "删除BdProductInfo")
    @DeleteMapping(value = "/bdProductInfo/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','BDPRODUCTINFO_ALL','BDPRODUCTINFO_DELETE')")
    public ResponseEntity delete(@PathVariable Integer id){
        productInfoService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}