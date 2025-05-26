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
package com.srr.service.impl;

import com.srr.domain.Event;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.srr.repository.EventRepository;
import com.srr.service.EventService;
import com.srr.dto.EventDto;
import com.srr.dto.EventQueryCriteria;
import com.srr.dto.mapstruct.EventMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import me.zhengjie.utils.PageResult;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author Chanheng
* @date 2025-05-18
**/
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public PageResult<EventDto> queryAll(EventQueryCriteria criteria, Pageable pageable){
        Page<Event> page = eventRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(eventMapper::toDto));
    }

    @Override
    public List<EventDto> queryAll(EventQueryCriteria criteria){
        return eventMapper.toDto(eventRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public EventDto findById(Long id) {
        Event event = eventRepository.findById(id).orElseGet(Event::new);
        ValidationUtil.isNull(event.getId(),"Event","id",id);
        return eventMapper.toDto(event);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Event resources) {
        eventRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Event resources) {
        Event event = eventRepository.findById(resources.getId()).orElseGet(Event::new);
        ValidationUtil.isNull( event.getId(),"Event","id",resources.getId());
        event.copy(resources);
        eventRepository.save(event);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            eventRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<EventDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (EventDto event : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", event.getName());
            map.put("描述", event.getDescription());
            map.put("SINGLE, DOUBLE", event.getFormat());
            map.put("最大人数", event.getMaxPlayer());
            map.put("位置", event.getLocation());
            map.put("图片", event.getImage());
            map.put("创建时间", event.getCreateTime());
            map.put("更新时间", event.getUpdateTime());
            map.put("排序", event.getSort());
            map.put("是否启用", event.getEnabled());
            map.put("时间", event.getEventTime());
            map.put(" clubId",  event.getClubId());
            map.put(" createBy",  event.getCreateBy());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}