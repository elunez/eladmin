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
package me.zhengjie.portfolio.room.service.impl;

import me.zhengjie.portfolio.room.domain.Room;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.portfolio.room.repository.RoomRepository;
import me.zhengjie.portfolio.room.service.RoomService;
import me.zhengjie.portfolio.room.service.dto.RoomDto;
import me.zhengjie.portfolio.room.service.dto.RoomQueryCriteria;
import me.zhengjie.portfolio.room.service.mapstruct.RoomMapper;
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
* @author Chanheng
* @date 2022-05-01
**/
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public Map<String,Object> queryAll(RoomQueryCriteria criteria, Pageable pageable){
        Page<Room> page = roomRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(roomMapper::toDto));
    }

    @Override
    public List<RoomDto> queryAll(RoomQueryCriteria criteria){
        return roomMapper.toDto(roomRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public RoomDto findById(Long id) {
        Room room = roomRepository.findById(id).orElseGet(Room::new);
        ValidationUtil.isNull(room.getId(),"Room","id",id);
        return roomMapper.toDto(room);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoomDto create(Room resources) {
        return roomMapper.toDto(roomRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Room resources) {
        Room room = roomRepository.findById(resources.getId()).orElseGet(Room::new);
        ValidationUtil.isNull( room.getId(),"Room","id",resources.getId());
        room.copy(resources);
        roomRepository.save(room);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            roomRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<RoomDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RoomDto room : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" name",  room.getName());
            map.put(" description",  room.getDescription());
            map.put(" images",  room.getImages());
            map.put(" extraInfo",  room.getExtraInfo());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}