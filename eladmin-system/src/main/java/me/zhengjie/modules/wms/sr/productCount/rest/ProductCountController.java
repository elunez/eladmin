package me.zhengjie.modules.wms.sr.productCount.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.sr.productCount.domain.ProductCount;
import me.zhengjie.modules.wms.sr.productCount.service.ProductCountService;
import me.zhengjie.modules.wms.sr.productCount.service.dto.ProductCountQueryCriteria;
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
* @date 2019-08-29
*/
@Api(tags = "产品统计管理")
@RestController
@RequestMapping("api")
public class ProductCountController {

    @Autowired
    private ProductCountService productCountService;

    @Log("分页查询产品统计列表")
    @ApiOperation(value = "分页查询产品统计列表")
    @GetMapping(value = "/queryProductCountPage")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_COUNT_ALL','PRODUCT_COUNT_SELECT')")
    public ResponseEntity queryProductCountPage(ProductCountQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(productCountService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增产品统计")
    @ApiOperation(value = "新增产品统计")
    @PostMapping(value = "/productCount")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_COUNT_ALL','PRODUCT_COUNT_CREATE')")
    public ResponseEntity create(@RequestBody ProductCount resources){
        return new ResponseEntity(productCountService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改产品统计")
    @ApiOperation(value = "修改产品统计")
    @PutMapping(value = "/productCount")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_COUNT_ALL','PRODUCT_COUNT_EDIT')")
    public ResponseEntity update(@RequestBody ProductCount resources){
        productCountService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除产品统计")
    @ApiOperation(value = "删除产品统计")
    @DeleteMapping(value = "/productCount/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_COUNT_ALL','PRODUCT_COUNT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        productCountService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查看产品统计详情")
    @GetMapping(value = "/productCount/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_COUNT_ALL','PRODUCT_COUNT_DETAIL_BY_ID')")
    public ResponseEntity getProductCountInfo(@PathVariable Long id){
        return new ResponseEntity(productCountService.findById(id), HttpStatus.OK);
    }
}