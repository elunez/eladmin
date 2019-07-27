package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.service.dto.DictDTO;
import me.zhengjie.modules.system.service.dto.RoleSmallDTO;
import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.bd.service.MeasureUnitService;
import me.zhengjie.modules.wms.bd.service.dto.MeasureUnitDTO;
import me.zhengjie.utils.SecurityUtils;
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
public class MeasureUnitController {

    @Autowired
    private MeasureUnitService measureUnitService;

    private static final String ENTITY_NAME = "measureUnit";

    @Log("新增计量单位")
    @PostMapping(value = "/measureUnit")
    public ResponseEntity create(@Validated @RequestBody MeasureUnit resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(measureUnitService.create(resources), HttpStatus.CREATED);
    }

    @Log("查看计量单位详情")
    @GetMapping(value = "/measureUnit/{id}")
    public ResponseEntity getMessureUnit(@PathVariable Long id){
        return new ResponseEntity(measureUnitService.findById(id), HttpStatus.OK);
    }

    @Log("删除计量单位")
    @DeleteMapping(value = "/measureUnit/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        measureUnitService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查询计量单位")
    @GetMapping(value = "/measureUnit")
    public ResponseEntity getMessureUnits(MeasureUnitDTO resources, Pageable pageable){
        return new ResponseEntity(measureUnitService.queryAll(resources,pageable),HttpStatus.OK);
    }
}
