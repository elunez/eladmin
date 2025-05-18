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

import com.srr.domain.Club;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.srr.repository.ClubRepository;
import com.srr.service.ClubService;
import com.srr.service.dto.ClubDto;
import com.srr.service.dto.ClubQueryCriteria;
import com.srr.service.mapstruct.ClubMapper;
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
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final ClubMapper clubMapper;

    @Override
    public PageResult<ClubDto> queryAll(ClubQueryCriteria criteria, Pageable pageable){
        Page<Club> page = clubRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(clubMapper::toDto));
    }

    @Override
    public List<ClubDto> queryAll(ClubQueryCriteria criteria){
        return clubMapper.toDto(clubRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ClubDto findById(Long id) {
        Club club = clubRepository.findById(id).orElseGet(Club::new);
        ValidationUtil.isNull(club.getId(),"Club","id",id);
        return clubMapper.toDto(club);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Club resources) {
        clubRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Club resources) {
        Club club = clubRepository.findById(resources.getId()).orElseGet(Club::new);
        ValidationUtil.isNull( club.getId(),"Club","id",resources.getId());
        club.copy(resources);
        clubRepository.save(club);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            clubRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ClubDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ClubDto club : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", club.getName());
            map.put("描述", club.getDescription());
            map.put("创建时间", club.getCreateTime());
            map.put("更新时间", club.getUpdateTime());
            map.put("图标", club.getIcon());
            map.put("排序", club.getSort());
            map.put("是否启用", club.getEnabled());
            map.put("位置", club.getLocation());
            map.put("经度", club.getLongitude());
            map.put("纬度", club.getLatitude());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}