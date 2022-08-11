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
package ${package}.service.impl;

import ${package}.domain.${className};
<#if columns??>
    <#list columns as column>
        <#if column.columnKey = 'UNI'>
            <#if column_index = 1>
import me.zhengjie.exception.EntityExistException;
            </#if>
        </#if>
    </#list>
</#if>
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import ${package}.repository.${className}Repository;
import ${package}.service.${className}Service;
import ${package}.service.dto.${className}Dto;
import ${package}.service.dto.${className}QueryCriteria;
import ${package}.service.mapstruct.${className}Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
<#if !auto && pkColumnType = 'Long'>
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
</#if>
<#if !auto && pkColumnType = 'String'>
import cn.hutool.core.util.IdUtil;
</#if>
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author ${author}
* @date ${date}
**/
@Service
@RequiredArgsConstructor
public class ${className}ServiceImpl implements ${className}Service {

    private final ${className}Repository ${changeClassName}Repository;
    private final ${className}Mapper ${changeClassName}Mapper;

    @Override
    public Map<String,Object> queryAll(${className}QueryCriteria criteria, Pageable pageable){
        Page<${className}> page = ${changeClassName}Repository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(${changeClassName}Mapper::toDto));
    }

    @Override
    public List<${className}Dto> queryAll(${className}QueryCriteria criteria){
        return ${changeClassName}Mapper.toDto(${changeClassName}Repository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ${className}Dto findById(${pkColumnType} ${pkChangeColName}) {
        ${className} ${changeClassName} = ${changeClassName}Repository.findById(${pkChangeColName}).orElseGet(${className}::new);
        ValidationUtil.isNull(${changeClassName}.get${pkCapitalColName}(),"${className}","${pkChangeColName}",${pkChangeColName});
        return ${changeClassName}Mapper.toDto(${changeClassName});
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${className}Dto create(${className} resources) {
<#if !auto && pkColumnType = 'Long'>
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.set${pkCapitalColName}(snowflake.nextId()); 
</#if>
<#if !auto && pkColumnType = 'String'>
        resources.set${pkCapitalColName}(IdUtil.simpleUUID()); 
</#if>
<#if columns??>
    <#list columns as column>
    <#if column.columnKey = 'UNI'>
        if(${changeClassName}Repository.findBy${column.capitalColumnName}(resources.get${column.capitalColumnName}()) != null){
            throw new EntityExistException(${className}.class,"${column.columnName}",resources.get${column.capitalColumnName}());
        }
    </#if>
    </#list>
</#if>
        return ${changeClassName}Mapper.toDto(${changeClassName}Repository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(${className} resources) {
        ${className} ${changeClassName} = ${changeClassName}Repository.findById(resources.get${pkCapitalColName}()).orElseGet(${className}::new);
        ValidationUtil.isNull( ${changeClassName}.get${pkCapitalColName}(),"${className}","id",resources.get${pkCapitalColName}());
<#if columns??>
    <#list columns as column>
        <#if column.columnKey = 'UNI'>
        <#if column_index = 1>
        ${className} ${changeClassName}1 = null;
        </#if>
        ${changeClassName}1 = ${changeClassName}Repository.findBy${column.capitalColumnName}(resources.get${column.capitalColumnName}());
        if(${changeClassName}1 != null && !${changeClassName}1.get${pkCapitalColName}().equals(${changeClassName}.get${pkCapitalColName}())){
            throw new EntityExistException(${className}.class,"${column.columnName}",resources.get${column.capitalColumnName}());
        }
        </#if>
    </#list>
</#if>
        ${changeClassName}.copy(resources);
        ${changeClassName}Repository.save(${changeClassName});
    }

    @Override
    public void deleteAll(${pkColumnType}[] ids) {
        for (${pkColumnType} ${pkChangeColName} : ids) {
            ${changeClassName}Repository.deleteById(${pkChangeColName});
        }
    }

    @Override
    public void download(List<${className}Dto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (${className}Dto ${changeClassName} : all) {
            Map<String,Object> map = new LinkedHashMap<>();
        <#list columns as column>
            <#if column.columnKey != 'PRI'>
            <#if column.remark != ''>
            map.put("${column.remark}", ${changeClassName}.get${column.capitalColumnName}());
            <#else>
            map.put(" ${column.changeColumnName}",  ${changeClassName}.get${column.capitalColumnName}());
            </#if>
            </#if>
        </#list>
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}