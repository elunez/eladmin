package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.SupplierCategory;
import me.zhengjie.modules.wms.bd.service.SupplierCategoryService;
import me.zhengjie.modules.wms.bd.service.dto.SupplierCategoryDTO;
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
@RestController("供应商类别管理")
@RequestMapping("api")
public class SupplierCategoryController {

    @Autowired
    private SupplierCategoryService supplierCategoryService;

    private static final String ENTITY_NAME = "supplierCategory";

    @Log("新增供应商类别")
    @PostMapping(value = "/supplierCategory")
    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER_CATEGORY_ALL','SUPPLIER_CATEGORY_CREATE')")
    public ResponseEntity create(@Validated @RequestBody SupplierCategory resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(supplierCategoryService.create(resources), HttpStatus.CREATED);
    }

    @Log("查看供应商类别详情")
    @GetMapping(value = "/supplierCategory/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER_CATEGORY_ALL','SUPPLIER_CATEGORY_DETAIL_BY_ID')")
    public ResponseEntity getMessureUnits(@PathVariable Long id){
        return new ResponseEntity(supplierCategoryService.findById(id), HttpStatus.OK);
    }

    @Log("删除供应商类别")
    @DeleteMapping(value = "/supplierCategory/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER_CATEGORY_ALL','SUPPLIER_CATEGORY_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        supplierCategoryService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("分页查询供应商类别")
    @GetMapping(value = "/querySupplierCategoryPage")
    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER_CATEGORY_ALL','SUPPLIER_CATEGORY_SELET')")
    public ResponseEntity querySupplierCategoryPage(SupplierCategoryDTO resources, Pageable pageable){
        return new ResponseEntity(supplierCategoryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("查询所有供应商类别")
    @GetMapping(value = "/queryAllCategoryList")
    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER_CATEGORY_ALL','SUPPLIER_CATEGORY_SELET')")
    public ResponseEntity queryAllCategoryList(SupplierCategoryDTO resources){
        return new ResponseEntity(supplierCategoryService.queryAll(resources),HttpStatus.OK);
    }
}
