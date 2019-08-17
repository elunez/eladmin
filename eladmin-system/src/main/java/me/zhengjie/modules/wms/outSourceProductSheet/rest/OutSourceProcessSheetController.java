package me.zhengjie.modules.wms.outSourceProductSheet.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import me.zhengjie.modules.wms.outSourceProductSheet.request.CreateOutSourceProcessSheetRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.service.OutSourceProcessSheetService;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author jie
* @date 2019-08-17
*/
@Api(tags = "委外加工管理")
@RestController
@RequestMapping("api")
public class OutSourceProcessSheetController {

    @Autowired
    private OutSourceProcessSheetService outSourceProcessSheetService;

    @Log("分页查询委外加工单")
    @ApiOperation(value = "分页查询委外加工单")
    @GetMapping(value = "/queryOutSourceProcessSheetPage")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEPROCESSSHEET_ALL','SOUTSOURCEPROCESSSHEET_SELECT')")
    public ResponseEntity queryOutSourceProcessSheetPage(OutSourceProcessSheetQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(outSourceProcessSheetService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增委外加工单")
    @ApiOperation(value = "新增委外加工单")
    @PostMapping(value = "/outSourceProcessSheet")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEPROCESSSHEET_ALL','SOUTSOURCEPROCESSSHEET_CREATE')")
    public ResponseEntity create(@RequestBody CreateOutSourceProcessSheetRequest createOutSourceProcessSheetRequest){
        return new ResponseEntity(outSourceProcessSheetService.create(createOutSourceProcessSheetRequest),HttpStatus.CREATED);
    }

    @Log("修改委外加工单")
    @ApiOperation(value = "修改委外加工单")
    @PutMapping(value = "/outSourceProcessSheet")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEPROCESSSHEET_ALL','SOUTSOURCEPROCESSSHEET_EDIT')")
    public ResponseEntity update(@Validated @RequestBody OutSourceProcessSheet resources){
        outSourceProcessSheetService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除委外加工单")
    @ApiOperation(value = "删除委外加工单")
    @DeleteMapping(value = "/outSourceProcessSheet/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SOUTSOURCEPROCESSSHEET_ALL','SOUTSOURCEPROCESSSHEET_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        outSourceProcessSheetService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}