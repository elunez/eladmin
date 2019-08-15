package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.WareHouse;
import me.zhengjie.modules.wms.bd.service.WareHouseService;
import me.zhengjie.modules.wms.bd.service.dto.WareHouseQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


/**
 * @author 黄星星
 * @date 2019-07-27
 */
@RestController("仓库管理")
@RequestMapping("api")
public class WareHouseController {

    @Autowired
    private WareHouseService wareHouseService;

    private static final String ENTITY_NAME = "wareHouse";

    @Log("新增仓库")
    @PostMapping(value = "/wareHouse")
    public ResponseEntity create(@Validated @RequestBody WareHouse resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(wareHouseService.create(resources), HttpStatus.CREATED);
    }


    @Log("初始化仓库编号")
    @GetMapping(value = "/initWareHouseCode")
    public ResponseEntity initWareHouseCode(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");//设置日期格式
        String supplierCode = "CK"+ LocalDateTime.now().format(fmt);
        return new ResponseEntity(supplierCode,HttpStatus.OK);
    }

    @Log("查看仓库详情")
    @GetMapping(value = "/wareHouse/{id}")
    public ResponseEntity getMessureUnits(@PathVariable Long id){
        return new ResponseEntity(wareHouseService.findById(id), HttpStatus.OK);
    }

    @Log("删除仓库")
    @DeleteMapping(value = "/wareHouse/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        wareHouseService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("分页查询仓库")
    @GetMapping(value = "/wareHouses")
    public ResponseEntity getWareHouses(WareHouseQueryCriteria wareHouseQueryCriteria, Pageable pageable){
        return new ResponseEntity(wareHouseService.queryAll(wareHouseQueryCriteria,pageable),HttpStatus.OK);
    }

    @Log("查询仓库列表")
    @GetMapping(value = "/queryWareHouseList")
    public ResponseEntity queryWareHouseList(WareHouseQueryCriteria wareHouseQueryCriteria){
        return new ResponseEntity(wareHouseService.queryAll(wareHouseQueryCriteria),HttpStatus.OK);
    }
}
