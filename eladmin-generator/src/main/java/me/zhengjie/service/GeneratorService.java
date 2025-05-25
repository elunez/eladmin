/*
 *  Copyright 2019-2025 Zheng Jie
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
package me.zhengjie.service;

import me.zhengjie.domain.GenConfig;
import me.zhengjie.domain.ColumnInfo;
import me.zhengjie.domain.vo.TableInfo;
import me.zhengjie.utils.PageResult;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2019-01-02
 */
public interface GeneratorService {

    /**
     * Query database metadata
     * @param name Table name
     * @param startEnd Pagination parameters
     * @return /
     */
    PageResult<TableInfo> getTables(String name, int[] startEnd);

    /**
     * Get table metadata
     * @param name Table name
     * @return /
     */
    List<ColumnInfo> getColumns(String name);

    /**
     * Synchronize table data
     * @param columnInfos 
     * @param columnInfoList 
     */
    void sync(List<ColumnInfo> columnInfos, List<ColumnInfo> columnInfoList);

    /**
     * Save data
     * @param columnInfos 
     */
    void save(List<ColumnInfo> columnInfos);

    /**
     * Get all tables
     * @return /
     */
    Object getTables();

    /**
     * Code generation
     * @param genConfig Configuration information
     * @param columns Field information
     */
    void generator(GenConfig genConfig, List<ColumnInfo> columns);

    /**
     * Preview
     * @param genConfig Configuration information
     * @param columns Field information
     * @return /
     */
    ResponseEntity<Object> preview(GenConfig genConfig, List<ColumnInfo> columns);

    /**
     * Download as package
     * @param genConfig Configuration information
     * @param columns Field information
     * @param request 
     * @param response 
     */
    void download(GenConfig genConfig, List<ColumnInfo> columns, HttpServletRequest request, HttpServletResponse response);

    /**
     * Query database table field data
     * @param table 
     * @return /
     */
    List<ColumnInfo> query(String table);
}
