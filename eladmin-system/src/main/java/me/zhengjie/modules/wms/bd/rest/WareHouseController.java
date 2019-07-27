package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.bd.domain.WareHouse;
import me.zhengjie.modules.wms.bd.service.MeasureUnitService;
import me.zhengjie.modules.wms.bd.service.WareHouseService;
import me.zhengjie.modules.wms.bd.service.dto.MeasureUnitDTO;
import me.zhengjie.modules.wms.bd.service.dto.WareHouseDTO;
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
public class WareHouseController {

    @Autowired
    private WareHouseService wareHouseService;

    private static final String ENTITY_NAME = "wareHouse";

    @Log("新增仓库")
    @PostMapping(value = "/warehouse")
    public ResponseEntity create(@Validated @RequestBody WareHouse resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(wareHouseService.create(resources), HttpStatus.CREATED);
    }

    @Log("查看仓库详情")
    @GetMapping(value = "/warehouse/{id}")
    public ResponseEntity getMessureUnits(@PathVariable Long id){
        return new ResponseEntity(wareHouseService.findById(id), HttpStatus.OK);
    }

    @Log("删除仓库")
    @DeleteMapping(value = "/warehouse/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        wareHouseService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查询仓库")
    @GetMapping(value = "/warehouse")
    public ResponseEntity getDicts(WareHouseDTO resources, Pageable pageable){
        return new ResponseEntity(wareHouseService.queryAll(resources,pageable),HttpStatus.OK);
    }
}
