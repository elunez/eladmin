package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.MaterialCategory;
import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.bd.service.MaterialCategoryService;
import me.zhengjie.modules.wms.bd.service.MeasureUnitService;
import me.zhengjie.modules.wms.bd.service.dto.MaterialCategoryDTO;
import me.zhengjie.modules.wms.bd.service.dto.MeasureUnitDTO;
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
@RestController
@RequestMapping("api")
public class MaterialCategoryController {

    @Autowired
    private MaterialCategoryService materialCategoryService;

    private static final String ENTITY_NAME = "materialCategory";

    @Log("新增物料类别")
    @PostMapping(value = "/measureUnit")
    public ResponseEntity create(@Validated @RequestBody MaterialCategory resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(materialCategoryService.create(resources), HttpStatus.CREATED);
    }

    @Log("查看物料类别")
    @GetMapping(value = "/measureUnit/{id}")
    public ResponseEntity getMessureUnits(@PathVariable Long id){
        return new ResponseEntity(materialCategoryService.findById(id), HttpStatus.OK);
    }

    @Log("删除物料类别")
    @DeleteMapping(value = "/measureUnit/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        materialCategoryService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查询物料类别")
    @GetMapping(value = "/measureUnit")
    public ResponseEntity getDicts(MaterialCategoryDTO resources, Pageable pageable){
        return new ResponseEntity(materialCategoryService.queryAll(resources,pageable),HttpStatus.OK);
    }
}
