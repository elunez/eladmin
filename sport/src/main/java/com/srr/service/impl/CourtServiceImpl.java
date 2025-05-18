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

import com.srr.domain.Court;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.srr.repository.CourtRepository;
import com.srr.service.CourtService;
import com.srr.service.dto.CourtDto;
import com.srr.service.dto.CourtQueryCriteria;
import com.srr.service.mapstruct.CourtMapper;
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
public class CourtServiceImpl implements CourtService {

    private final CourtRepository courtRepository;
    private final CourtMapper courtMapper;

    @Override
    public PageResult<CourtDto> queryAll(CourtQueryCriteria criteria, Pageable pageable){
        Page<Court> page = courtRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(courtMapper::toDto));
    }

    @Override
    public List<CourtDto> queryAll(CourtQueryCriteria criteria){
        return courtMapper.toDto(courtRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CourtDto findById(Long id) {
        Court court = courtRepository.findById(id).orElseGet(Court::new);
        ValidationUtil.isNull(court.getId(),"Court","id",id);
        return courtMapper.toDto(court);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Court resources) {
        courtRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Court resources) {
        Court court = courtRepository.findById(resources.getId()).orElseGet(Court::new);
        ValidationUtil.isNull( court.getId(),"Court","id",resources.getId());
        court.copy(resources);
        courtRepository.save(court);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            courtRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<CourtDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CourtDto court : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" clubId",  court.getClubId());
            map.put(" sportId",  court.getSportId());
            map.put("创建时间", court.getCreateTime());
            map.put("更新时间", court.getUpdateTime());
            map.put("数量", court.getAmount());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}