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

import com.uueo.itam.ticket.domain.Event;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.uueo.itam.ticket.repository.EventRepository;
import com.uueo.itam.ticket.service.EventService;
import com.uueo.itam.ticket.service.dto.EventDto;
import com.uueo.itam.ticket.service.dto.EventQueryCriteria;
import com.uueo.itam.ticket.service.mapstruct.EventMapper;
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

/**
* @website https://el-admin.vip
* @description 服务实现
* @author esinhee
* @date 2021-01-19
**/
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public Map<String,Object> queryAll(EventQueryCriteria criteria, Pageable pageable){
        Page<Event> page = eventRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(eventMapper::toDto));
    }

    @Override
    public List<EventDto> queryAll(EventQueryCriteria criteria){
        return eventMapper.toDto(eventRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public EventDto findById(Integer eid) {
        Event event = eventRepository.findById(eid).orElseGet(Event::new);
        ValidationUtil.isNull(event.getEid(),"Event","eid",eid);
        return eventMapper.toDto(event);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EventDto create(Event resources) {
        return eventMapper.toDto(eventRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Event resources) {
        Event event = eventRepository.findById(resources.getEid()).orElseGet(Event::new);
        ValidationUtil.isNull( event.getEid(),"Event","id",resources.getEid());
        event.copy(resources);
        eventRepository.save(event);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer eid : ids) {
            eventRepository.deleteById(eid);
        }
    }

    @Override
    public void download(List<EventDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (EventDto event : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("事件类型", event.getEtid());
            map.put("重要程度", event.getGrade());
            map.put("详情描述", event.getContent());
            map.put("反馈科室", event.getKid());
            map.put("反馈人", event.getReportid());
            map.put("反馈时间", event.getReporttime());
            map.put("记录人", event.getRecorderid());
            map.put("记录时间", event.getRecordetime());
            map.put("处理人", event.getDealerid());
            map.put("处理时间", event.getDealtime());
            map.put("最后更新时间", event.getUpdatetime());
            map.put("处理标识", event.getDealflg());
            map.put("关联事件ID", event.getRelatedid());
            map.put("状态标识", event.getBz());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}