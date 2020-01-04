package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.bd.domain.ProductSeries;
import me.zhengjie.modules.wms.bd.service.ProductSeriesService;
import me.zhengjie.modules.wms.bd.service.dto.ProductSeriesQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author huangxingxing
* @date 2020-01-04
*/
@Api(tags = "产品系列管理")
@RestController
@RequestMapping("api")
public class ProductSeriesController {

    @Autowired
    private ProductSeriesService productSeriesService;

    @Log("分页查询产品系列")
    @ApiOperation(value = "分页查询产品系列")
    @GetMapping(value = "/queryProductSeriesPage")
    @PreAuthorize("hasAnyRole('ADMIN','BDPRODUCTSERIES_ALL','BDPRODUCTSERIES_SELECT')")
    public ResponseEntity queryProductSeriesPage(ProductSeriesQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(productSeriesService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("产品系列列表")
    @ApiOperation(value = "产品系列列表")
    @GetMapping(value = "/queryProductSeriesList")
    @PreAuthorize("hasAnyRole('ADMIN','BDPRODUCTSERIES_ALL','BDPRODUCTSERIES_SELECT')")
    public ResponseEntity queryProductSeriesList(ProductSeriesQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(productSeriesService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增产品系列")
    @ApiOperation(value = "新增产品系列")
    @PostMapping(value = "/productSeries")
    @PreAuthorize("hasAnyRole('ADMIN','BDPRODUCTSERIES_ALL','BDPRODUCTSERIES_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ProductSeries resources){
        return new ResponseEntity(productSeriesService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改产品系列")
    @ApiOperation(value = "修改产品系列")
    @PutMapping(value = "/productSeries")
    @PreAuthorize("hasAnyRole('ADMIN','BDPRODUCTSERIES_ALL','BDPRODUCTSERIES_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ProductSeries resources){
        productSeriesService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除产品系列")
    @ApiOperation(value = "删除产品系列")
    @DeleteMapping(value = "/productSeries/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','BDPRODUCTSERIES_ALL','BDPRODUCTSERIES_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        productSeriesService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}