package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.ProductCategory;
import me.zhengjie.modules.wms.bd.service.ProductCategoryService;
import me.zhengjie.modules.wms.bd.service.dto.ProductCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author 黄星星
 * @date 2019-07-27
 */
@RestController("产品类别管理")
@RequestMapping("api")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    private static final String ENTITY_NAME = "productCategory";

    @Log("新增产品类别")
    @PostMapping(value = "/productCategory")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_CATEGORY_ALL','PRODUCT_CATEGORY_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ProductCategory resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(productCategoryService.create(resources), HttpStatus.CREATED);
    }

    @Log("查看产品类别详情")
    @GetMapping(value = "/productCategory/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_CATEGORY_ALL','PRODUCT_CATEGORY_DETAIL_BY_ID')")
    public ResponseEntity getMessureUnits(@PathVariable Long id){
        return new ResponseEntity(productCategoryService.findById(id), HttpStatus.OK);
    }

    @Log("删除产品类别")
    @DeleteMapping(value = "/productCategory/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_CATEGORY_ALL','PRODUCT_CATEGORY_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        productCategoryService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查询产品类别列表")
    @GetMapping(value = "/queryProductCategoryList")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_CATEGORY_ALL','PRODUCT_CATEGORY_SELECT')")
    public ResponseEntity queryProductCategoryList(ProductCategoryDTO resources){
        return new ResponseEntity(productCategoryService.queryAll(resources),HttpStatus.OK);
    }

    @Log("分页查询产品类别")
    @GetMapping(value = "/queryProductCategoryPage")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_CATEGORY_ALL','PRODUCT_CATEGORY_SELECT')")
    public ResponseEntity queryProductCategoryPage(ProductCategoryDTO resources, Pageable pageable){
        return new ResponseEntity(productCategoryService.queryAll(resources,pageable),HttpStatus.OK);
    }
}
