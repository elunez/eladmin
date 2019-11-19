package me.zhengjie.gen.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.gen.domain.GenTest;
import me.zhengjie.gen.service.GenTestService;
import me.zhengjie.gen.service.dto.GenTestQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author Zheng Jie
* @date 2019-11-19
*/
@Api(tags = "GenTest管理")
@RestController
@RequestMapping("/api/genTest")
public class GenTestController {

    private final GenTestService genTestService;

    public GenTestController(GenTestService genTestService) {
        this.genTestService = genTestService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('genTest:list')")
    public void download(HttpServletResponse response, GenTestQueryCriteria criteria) throws IOException {
        genTestService.download(genTestService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询GenTest")
    @ApiOperation("查询GenTest")
    @PreAuthorize("@el.check('genTest:list')")
    public ResponseEntity getGenTests(GenTestQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(genTestService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增GenTest")
    @ApiOperation("新增GenTest")
    @PreAuthorize("@el.check('genTest:add')")
    public ResponseEntity create(@Validated @RequestBody GenTest resources){
        return new ResponseEntity<>(genTestService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改GenTest")
    @ApiOperation("修改GenTest")
    @PreAuthorize("@el.check('genTest:edit')")
    public ResponseEntity update(@Validated @RequestBody GenTest resources){
        genTestService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    @Log("删除GenTest")
    @ApiOperation("删除GenTest")
    @PreAuthorize("@el.check('genTest:del')")
    public ResponseEntity delete(@PathVariable Long id){
        genTestService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}