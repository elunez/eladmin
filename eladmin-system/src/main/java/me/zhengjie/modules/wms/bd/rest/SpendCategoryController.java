package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.SpendCategory;
import me.zhengjie.modules.wms.bd.service.SpendCategoryService;
import me.zhengjie.modules.wms.bd.service.dto.SpendCategoryDTO;
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
@RestController("支出类别管理")
@RequestMapping("api")
public class SpendCategoryController {

    @Autowired
    private SpendCategoryService spendCategoryService;

    private static final String ENTITY_NAME = "spendCategory";

    @Log("新增支出类别")
    @PostMapping(value = "/spendCategory")
    @PreAuthorize("hasAnyRole('ADMIN','SPEND_CATEGORY_ALL','SPEND_CATEGORY_CREATE')")
    public ResponseEntity create(@Validated @RequestBody SpendCategory resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(spendCategoryService.create(resources), HttpStatus.CREATED);
    }

    @Log("查看支出类别详情")
    @GetMapping(value = "/spendCategory/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SPEND_CATEGORY_ALL','SPEND_CATEGORY_DETAIL_BY_ID')")
    public ResponseEntity getMessureUnits(@PathVariable Long id){
        return new ResponseEntity(spendCategoryService.findById(id), HttpStatus.OK);
    }

    @Log("删除支出类别")
    @DeleteMapping(value = "/spendCategory/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SPEND_CATEGORY_ALL','SPEND_CATEGORY_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        spendCategoryService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查询支出类别")
    @GetMapping(value = "/querySpendCategoryPage")
    @PreAuthorize("hasAnyRole('ADMIN','SPEND_CATEGORY_ALL','SPEND_CATEGORY_SELECT')")
    public ResponseEntity querySpendCategoryPage(SpendCategoryDTO resources, Pageable pageable){
        return new ResponseEntity(spendCategoryService.queryAll(resources,pageable),HttpStatus.OK);
    }
}
