package me.zhengjie.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.domain.LocalStorage;
import me.zhengjie.service.LocalStorageService;
import me.zhengjie.service.dto.LocalStorageQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Api(tags = "本地存储管理")
@RestController
@RequestMapping("api")
public class LocalStorageController {

    @Autowired
    private LocalStorageService localStorageService;

    @ApiOperation(value = "查询文件")
    @GetMapping(value = "/localStorage")
    @PreAuthorize("hasAnyRole('ADMIN','LOCALSTORAGE_ALL','LOCALSTORAGE_SELECT')")
    public ResponseEntity getLocalStorages(LocalStorageQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(localStorageService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/localStorage")
    @PreAuthorize("hasAnyRole('ADMIN','LOCALSTORAGE_ALL','LOCALSTORAGE_CREATE')")
    public ResponseEntity create(@RequestParam String name, @RequestParam("file") MultipartFile file){
        return new ResponseEntity(localStorageService.create(name, file),HttpStatus.CREATED);
    }

    @ApiOperation(value = "修改文件")
    @PutMapping(value = "/localStorage")
    @PreAuthorize("hasAnyRole('ADMIN','LOCALSTORAGE_ALL','LOCALSTORAGE_EDIT')")
    public ResponseEntity update(@Validated @RequestBody LocalStorage resources){
        localStorageService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "删除文件")
    @DeleteMapping(value = "/localStorage/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LOCALSTORAGE_ALL','LOCALSTORAGE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        localStorageService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除多张图片
     * @param ids
     * @return
     */
    @Log("删除图片")
    @DeleteMapping(value = "/localStorage")
    public ResponseEntity deleteAll(@RequestBody Long[] ids) {
        localStorageService.deleteAll(ids);
        return new ResponseEntity(HttpStatus.OK);
    }
}