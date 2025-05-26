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
package com.srr.service;

import com.srr.domain.Court;
import com.srr.dto.CourtDto;
import com.srr.dto.CourtQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import me.zhengjie.utils.PageResult;

/**
* @website https://eladmin.vip
* @description Service Interface
* @author Chanheng
* @date 2025-05-18
**/
public interface CourtService {

    /**
    * Query data with pagination
    * @param criteria criteria
    * @param pageable pagination parameters
    * @return Map<String,Object>
    */
    PageResult<CourtDto> queryAll(CourtQueryCriteria criteria, Pageable pageable);

    /**
    * Query all data without pagination
    * @param criteria criteria parameters
    * @return List<CourtDto>
    */
    List<CourtDto> queryAll(CourtQueryCriteria criteria);

    /**
     * Query by ID
     * @param id ID
     * @return CourtDto
     */
    CourtDto findById(Long id);

    /**
    * Create
    * @param resources /
    */
    void create(Court resources);

    /**
    * Edit
    * @param resources /
    */
    void update(Court resources);

    /**
    * Multi-select delete
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * Export data
    * @param all data to be exported
    * @param response /
    * @throws IOException /
    */
    void download(List<CourtDto> all, HttpServletResponse response) throws IOException;
}