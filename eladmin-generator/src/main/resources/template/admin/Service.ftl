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
package ${package}.service;

import ${package}.domain.${className};
import ${package}.service.dto.${className}Dto;
import ${package}.service.dto.${className}QueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import me.zhengjie.utils.PageResult;

/**
* @website https://eladmin.vip
* @description Service Interface
* @author ${author}
* @date ${date}
**/
public interface ${className}Service {

    /**
    * Query data with pagination
    * @param criteria criteria
    * @param pageable pagination parameters
    * @return Map<String,Object>
    */
    PageResult<${className}Dto> queryAll(${className}QueryCriteria criteria, Pageable pageable);

    /**
    * Query all data without pagination
    * @param criteria criteria parameters
    * @return List<${className}Dto>
    */
    List<${className}Dto> queryAll(${className}QueryCriteria criteria);

    /**
     * Query by ID
     * @param ${pkChangeColName} ID
     * @return ${className}Dto
     */
    ${className}Dto findById(${pkColumnType} ${pkChangeColName});

    /**
    * Create
    * @param resources /
    */
    void create(${className} resources);

    /**
    * Edit
    * @param resources /
    */
    void update(${className} resources);

    /**
    * Multi-select delete
    * @param ids /
    */
    void deleteAll(${pkColumnType}[] ids);

    /**
    * Export data
    * @param all data to be exported
    * @param response /
    * @throws IOException /
    */
    void download(List<${className}Dto> all, HttpServletResponse response) throws IOException;
}