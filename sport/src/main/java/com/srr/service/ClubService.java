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

import com.srr.domain.Club;
import com.srr.service.dto.ClubDto;
import com.srr.service.dto.ClubQueryCriteria;
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
public interface ClubService {

    /**
    * Query data with pagination
    * @param criteria criteria
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<ClubDto> queryAll(ClubQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<ClubDto>
    */
    List<ClubDto> queryAll(ClubQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return ClubDto
     */
    ClubDto findById(Long id);

    /**
    * 创建
    * @param resources /
    */
    void create(Club resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Club resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<ClubDto> all, HttpServletResponse response) throws IOException;
}