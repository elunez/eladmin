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
package com.uueo.itam.ticket.service;

import com.uueo.itam.ticket.domain.EventType;
import com.uueo.itam.ticket.service.dto.EventTypeDto;
import com.uueo.itam.ticket.service.dto.EventTypeQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author esinhee
* @date 2021-01-20
**/
public interface EventTypeService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(EventTypeQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<EventTypeDto>
    */
    List<EventTypeDto> queryAll(EventTypeQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return EventTypeDto
     */
    EventTypeDto findById(Integer id);

    /**
    * 创建
    * @param resources /
    * @return EventTypeDto
    */
    EventTypeDto create(EventType resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(EventType resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Integer[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<EventTypeDto> all, HttpServletResponse response) throws IOException;

    /**
     * 根据parentid获取子项目
     * @param parentid
     * @return List<EventTypeDto>
     */
    List<EventTypeDto> getEventTypes(Integer parentid);

    /**
     * 获取上级事件类型
     * @param etDto
     * @param objects
     * @return
     */
    List<EventTypeDto> getSuperior(EventTypeDto etDto, List<EventType> objects);

    /**
     * 生成树形结构
     * @param ets
     * @return
     */
    List<EventTypeDto> buildTree(List<EventTypeDto> ets);
}