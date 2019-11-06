package me.zhengjie.modules.system.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.config.DataScope;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.FileSort;
import me.zhengjie.modules.system.service.FileSortService;
import me.zhengjie.modules.system.service.dto.FileSortDTO;
import me.zhengjie.modules.system.service.dto.FileSortQueryCriteria;
import me.zhengjie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@RestController
@RequestMapping("api")
public class FileSortController {

    @Autowired
    private FileSortService fileSortService;

    @Autowired
    private DataScope dataScope;

    private static final String ENTITY_NAME = "fileSort";

    @Log("查询目录")
    @GetMapping(value = "/fileSort")
    @PreAuthorize("hasAnyRole('ADMIN','FILESORT_ALL','FILESORT_SELECT')")
    public ResponseEntity getFileSorts(FileSortQueryCriteria criteria){
        // 数据权限
        //criteria.setIds(dataScope.getDeptIds());
        List<FileSortDTO> fileSortDTOS = fileSortService.queryAll(criteria);
        return new ResponseEntity(fileSortService.buildTree(fileSortDTOS),HttpStatus.OK);
    }

    @Log("新增部门")
    @PostMapping(value = "/fileSort")
    @PreAuthorize("hasAnyRole('ADMIN','FILESORT_ALL','FILESORT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody FileSort resources){
        if (StringUtils.isNotEmpty(resources.getId())) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(fileSortService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改部门")
    @PutMapping(value = "/fileSort")
    @PreAuthorize("hasAnyRole('ADMIN','FILESORT_ALL','FILESORT_EDIT')")
    public ResponseEntity update(@Validated(FileSort.Update.class) @RequestBody FileSort resources){
        fileSortService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除部门")
    @DeleteMapping(value = "/fileSort/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FILESORT_ALL','FILESORT_DELETE')")
    public ResponseEntity delete(@PathVariable String id){
        fileSortService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}