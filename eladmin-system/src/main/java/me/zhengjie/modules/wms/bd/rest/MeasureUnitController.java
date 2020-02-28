package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.bd.service.MeasureUnitService;
import me.zhengjie.modules.wms.bd.service.dto.MeasureUnitDTO;
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
@RestController("计量单位管理")
@RequestMapping("api")
public class MeasureUnitController {

    @Autowired
    private MeasureUnitService measureUnitService;

    private static final String ENTITY_NAME = "measureUnit";

    @Log("新增计量单位")
    @PostMapping(value = "/measureUnit")
    @PreAuthorize("hasAnyRole('ADMIN','MEASUREE_UNIT_ALL','MEASUREE_UNIT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody MeasureUnit resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(measureUnitService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改计量单位")
    @PostMapping(value = "/measureUnit/update")
    @PreAuthorize("hasAnyRole('ADMIN','MEASUREE_UNIT_ALL','MEASUREE_UNIT_EDIT')")
    public ResponseEntity updateMeasureUnit(@RequestBody MeasureUnit resources){
        if (resources.getId() == null) {
            throw new BadRequestException("主键不能为空");
        }
        return new ResponseEntity(measureUnitService.create(resources), HttpStatus.CREATED);
    }

    @Log("查看计量单位详情")
    @GetMapping(value = "/measureUnit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEASUREE_UNIT_ALL','MEASUREE_UNIT_DETAIL_BY_ID')")
    public ResponseEntity getMessureUnit(@PathVariable Long id){
        return new ResponseEntity(measureUnitService.findById(id), HttpStatus.OK);
    }

    @Log("删除计量单位")
    @DeleteMapping(value = "/measureUnit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEASUREE_UNIT_ALL','MEASUREE_UNIT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        measureUnitService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("分页查询计量单位")
    @GetMapping(value = "/queryMeasureUnitPage")
    @PreAuthorize("hasAnyRole('ADMIN','MEASUREE_UNIT_ALL','MEASUREE_UNIT_SELECT')")
    public ResponseEntity queryMeasureUnitPage(MeasureUnitDTO resources, Pageable pageable){
        return new ResponseEntity(measureUnitService.queryAll(resources,pageable),HttpStatus.OK);
    }


    @Log("查询所有计量单位")
    @GetMapping(value = "/queryMeasureUnitList")
    @PreAuthorize("hasAnyRole('ADMIN','MEASUREE_UNIT_ALL','MEASUREE_UNIT_SELECT')")
    public ResponseEntity queryMeasureUnitList(MeasureUnitDTO resources){
        return new ResponseEntity(measureUnitService.queryAll(resources),HttpStatus.OK);
    }
}
