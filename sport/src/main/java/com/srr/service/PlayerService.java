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

import com.srr.domain.Player;
import com.srr.service.dto.PlayerDto;
import com.srr.service.dto.PlayerQueryCriteria;
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
public interface PlayerService {

    /**
    * Query data with pagination
    * @param criteria criteria
    * @param pageable pagination parameters
    * @return Map<String,Object>
    */
    PageResult<PlayerDto> queryAll(PlayerQueryCriteria criteria, Pageable pageable);

    /**
    * Query all data without pagination
    * @param criteria criteria parameters
    * @return List<PlayerDto>
    */
    List<PlayerDto> queryAll(PlayerQueryCriteria criteria);

    /**
     * Query by ID
     * @param id ID
     * @return PlayerDto
     */
    PlayerDto findById(Long id);

    /**
    * Create
    * @param resources /
    */
    void create(Player resources);

    /**
    * Edit
    * @param resources /
    */
    void update(Player resources);

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
    void download(List<PlayerDto> all, HttpServletResponse response) throws IOException;
}