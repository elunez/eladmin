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
package com.uueo.itam.ticket.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.uueo.itam.ticket.domain.EventType;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.uueo.itam.ticket.repository.EventTypeRepository;
import com.uueo.itam.ticket.service.EventTypeService;
import com.uueo.itam.ticket.service.dto.EventTypeDto;
import com.uueo.itam.ticket.service.dto.EventTypeQueryCriteria;
import com.uueo.itam.ticket.service.mapstruct.EventTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author esinhee
* @date 2021-01-20
**/
@Service
@RequiredArgsConstructor
public class EventTypeServiceImpl implements EventTypeService {

    private final EventTypeRepository eventTypeRepository;
    private final EventTypeMapper eventTypeMapper;

    @Override
    public Map<String,Object> queryAll(EventTypeQueryCriteria criteria, Pageable pageable){
        /*
        eventTypeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder))
        等效于
        eventTypeRepository.findAll(new Specification() {
            @Override
            Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return QueryHelp.getPredicate(root,criteria,criteriaBuilder);
            }
        });
        */
        if (ObjectUtil.isNull(criteria.getParentid())) {
            criteria.setParentid(0);
        }

        Page<EventType> page = eventTypeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(eventTypeMapper::toDto));
    }

    @Override
    public List<EventTypeDto> queryAll(EventTypeQueryCriteria criteria){
        return eventTypeMapper.toDto(eventTypeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public EventTypeDto findById(Integer id) {
        EventType eventType = eventTypeRepository.findById(id).orElseGet(EventType::new);
        ValidationUtil.isNull(eventType.getId(),"EventType","id",id);
        return eventTypeMapper.toDto(eventType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EventTypeDto create(EventType resources) {
        return eventTypeMapper.toDto(eventTypeRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(EventType resources) {
        EventType eventType = eventTypeRepository.findById(resources.getId()).orElseGet(EventType::new);
        ValidationUtil.isNull( eventType.getId(),"EventType","id",resources.getId());
        eventType.copy(resources);
        eventTypeRepository.save(eventType);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            eventTypeRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<EventTypeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (EventTypeDto eventType : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("事件类型名称", eventType.getTypename());
            map.put(" parentid",  eventType.getParentid());
            map.put("层级路径", eventType.getLevel());
            map.put("层级", eventType.getSeq());
            map.put("拼音", eventType.getPinyin());
            map.put("启用标识", eventType.getBz());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<EventTypeDto> getEventTypes(Integer parentid) {
        List<EventType> et;
        if (parentid != null || !parentid.equals(0)) {
            et = eventTypeRepository.findByParentid(parentid);
        } else {
            et = eventTypeRepository.findByParentid(0);
        }
        return eventTypeMapper.toDto(et);
    }

    @Override
    public List<EventTypeDto> getSuperior(EventTypeDto etDto, List<EventType> ets) {
        if (etDto.getParentid() == null || etDto.getParentid() == 0) {
            ets.addAll(eventTypeRepository.findByParentid(0));
            return eventTypeMapper.toDto(ets);
        }
        ets.addAll(eventTypeRepository.findByParentid(etDto.getParentid()));
        return getSuperior(findById(etDto.getParentid()), ets);
    }

    @Override
    public List<EventTypeDto> buildTree(List<EventTypeDto> ets) {
        List<EventTypeDto> trees = new ArrayList<>();
        Set<Integer> ids = new HashSet<>();
        for (EventTypeDto et : ets) {
            if (et.getParentid() == null || et.getParentid() == 0) {
                trees.add(et);
            }
            for (EventTypeDto id : ets) {
                if (id.getParentid().equals(et.getId())) {
                    if (et.getChildren() == null) {
                        et.setChildren(new ArrayList<>());
                    }
                    et.getChildren().add(id);
                    ids.add(id.getId());
                }
            }
        }
        if (trees.size() == 0) {
            trees = ets.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        return trees;
    }
}