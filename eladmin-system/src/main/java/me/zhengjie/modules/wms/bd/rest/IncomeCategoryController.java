package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.IncomeCategory;
import me.zhengjie.modules.wms.bd.service.IncomeCategoryService;
import me.zhengjie.modules.wms.bd.service.dto.IncomeCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author 黄星星
 * @date 2019-07-27
 */
@RestController("收入类别管理")
@RequestMapping("api")
public class IncomeCategoryController {

    @Autowired
    private IncomeCategoryService incomeCategoryService;

    private static final String ENTITY_NAME = "incomeCategory";

    @Log("新增收入分类")
    @PostMapping(value = "/incomeCategory")
    public ResponseEntity create(@Validated @RequestBody IncomeCategory resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(incomeCategoryService.create(resources), HttpStatus.CREATED);
    }

    @Log("查看收入分类详情")
    @GetMapping(value = "/incomeCategory/{id}")
    public ResponseEntity getIncomeCategory(@PathVariable Long id){
        return new ResponseEntity(incomeCategoryService.findById(id), HttpStatus.OK);
    }

    @Log("删除收入分类")
    @DeleteMapping(value = "/incomeCategory/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        incomeCategoryService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查询收入分类")
    @GetMapping(value = "/incomeCategory")
    public ResponseEntity getIncomeCategorys(IncomeCategoryDTO resources, Pageable pageable){
        return new ResponseEntity(incomeCategoryService.queryAll(resources,pageable),HttpStatus.OK);
    }
}
