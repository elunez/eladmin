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
* @date 2020-03-07
*/
@Api(tags = "测试生成管理")
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
    @Log("查询测试生成")
    @ApiOperation("查询测试生成")
    @PreAuthorize("@el.check('genTest:list')")
    public ResponseEntity<Object> getGenTests(GenTestQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(genTestService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增测试生成")
    @ApiOperation("新增测试生成")
    @PreAuthorize("@el.check('genTest:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody GenTest resources){
        return new ResponseEntity<>(genTestService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改测试生成")
    @ApiOperation("修改测试生成")
    @PreAuthorize("@el.check('genTest:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody GenTest resources){
        genTestService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除测试生成")
    @ApiOperation("删除测试生成")
    @PreAuthorize("@el.check('genTest:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Integer[] ids) {
        genTestService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}