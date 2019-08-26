package me.zhengjie.modules.wms.bd.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.bd.domain.MaterialInfo;
import me.zhengjie.modules.wms.bd.request.CreateMaterialInfoRequest;
import me.zhengjie.modules.wms.bd.request.UpdateMaterialInfoRequest;
import me.zhengjie.modules.wms.bd.service.MaterialInfoService;
import me.zhengjie.modules.wms.bd.service.dto.MaterialInfoQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
* @author 黄星星
* @date 2019-07-27
*/
@Api(tags = "物料资料管理")
@RestController
@RequestMapping("api")
public class MaterialInfoController {

    @Autowired
    private MaterialInfoService materialInfoService;
    @Log("初始化物料资料编号")
    @ApiOperation(value = "初始化物料资料编号")
    @GetMapping(value = "/initMaterialInfoCode")
    @PreAuthorize("hasAnyRole('ADMIN','BDSUPPLIERINFO_ALL','BDSUPPLIERINFO_SELECT')")
    public ResponseEntity initMaterialInfoCode(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");//设置日期格式
        String supplierCode = "WL"+ LocalDateTime.now().format(fmt);
        return new ResponseEntity(supplierCode,HttpStatus.OK);
    }

    @Log("分页查询物料资料")
    @ApiOperation(value = "分页查询物料资料")
    @GetMapping(value = "/queryMaterialInfoPage")
    public ResponseEntity queryMaterialInfoPage(MaterialInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(materialInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("查询所有物料资料")
    @ApiOperation(value = "查询所有物料资料")
    @GetMapping(value = "/queryMaterialInfoList")
    public ResponseEntity queryMaterialInfoList(MaterialInfoQueryCriteria criteria){
        return new ResponseEntity(materialInfoService.queryAll(criteria),HttpStatus.OK);
    }

    @Log("新增物料资料")
    @ApiOperation(value = "新增物料资料")
    @PostMapping(value = "/materialInfo")
    public ResponseEntity create(@RequestBody CreateMaterialInfoRequest createMaterialInfoRequest){
        return new ResponseEntity(materialInfoService.create(createMaterialInfoRequest),HttpStatus.CREATED);
    }

    @Log("修改物料资料")
    @ApiOperation(value = "修改物料资料")
    @PutMapping(value = "/materialInfo/update")
    public ResponseEntity update(@RequestBody UpdateMaterialInfoRequest updateMaterialInfoRequest){
        materialInfoService.update(updateMaterialInfoRequest);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除物料资料")
    @ApiOperation(value = "删除物料资料")
    @DeleteMapping(value = "/materialInfo/{id}")
    public ResponseEntity deleteMaterialInfoById(@PathVariable Integer id){
        materialInfoService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查看物料资料详情")
    @GetMapping(value = "/materialInfo/{id}")
    public ResponseEntity getMaterialInfoById(@PathVariable Long id){
        return new ResponseEntity(materialInfoService.findById(id), HttpStatus.OK);
    }
}