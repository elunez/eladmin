/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.fin.stock.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.fin.stock.domain.Stock;
import me.zhengjie.fin.stock.service.StockService;
import me.zhengjie.fin.stock.service.dto.StockQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author zhangjc
 * @date 2023-02-14
 */
@RestController
@Api(tags = "理财：股票")
@RequiredArgsConstructor
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    @ApiOperation("导出股票数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('stock:list')")
    public void exportStock(HttpServletResponse response, StockQueryCriteria criteria) throws IOException {
        stockService.download(stockService.queryAll(criteria), response);
    }

    @ApiOperation(value = "查询服务器")
    @GetMapping
    @PreAuthorize("@el.check('Stock:list')")
    public ResponseEntity<Object> queryStock(StockQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(stockService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增服务器")
    @ApiOperation(value = "新增服务器")
    @PostMapping
    @PreAuthorize("@el.check('Stock:add')")
    public ResponseEntity<Object> createStock(@Validated @RequestBody Stock resources) {
        stockService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改服务器")
    @ApiOperation(value = "修改服务器")
    @PutMapping
    @PreAuthorize("@el.check('Stock:edit')")
    public ResponseEntity<Object> updateStock(@Validated @RequestBody Stock resources) {
        stockService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除服务器")
    @ApiOperation(value = "删除Server")
    @DeleteMapping
    @PreAuthorize("@el.check('Stock:del')")
    public ResponseEntity<Object> deleteStock(@RequestBody Set<Long> ids) {
        stockService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("测试连接服务器")
    @ApiOperation(value = "测试连接服务器")
    @PostMapping("/testConnect")
    @PreAuthorize("@el.check('Stock:add')")
    public ResponseEntity<Object> testConnectStock(@Validated @RequestBody Stock resources) {
        return new ResponseEntity<>(stockService.testConnect(resources), HttpStatus.CREATED);
    }
}
