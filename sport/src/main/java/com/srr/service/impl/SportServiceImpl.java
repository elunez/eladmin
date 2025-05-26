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

import com.srr.domain.Sport;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.srr.repository.SportRepository;
import com.srr.service.SportService;
import com.srr.dto.SportDto;
import com.srr.dto.SportQueryCriteria;
import com.srr.dto.mapstruct.SportMapper;
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
* @date 2025-05-17
**/
@Service
@RequiredArgsConstructor
public class SportServiceImpl implements SportService {

    private final SportRepository sportRepository;
    private final SportMapper sportMapper;

    @Override
    public PageResult<SportDto> queryAll(SportQueryCriteria criteria, Pageable pageable){
        Page<Sport> page = sportRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(sportMapper::toDto));
    }

    @Override
    public List<SportDto> queryAll(SportQueryCriteria criteria){
        return sportMapper.toDto(sportRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SportDto findById(Long id) {
        Sport sport = sportRepository.findById(id).orElseGet(Sport::new);
        ValidationUtil.isNull(sport.getId(),"Sport","id",id);
        return sportMapper.toDto(sport);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Sport resources) {
        sportRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Sport resources) {
        Sport sport = sportRepository.findById(resources.getId()).orElseGet(Sport::new);
        ValidationUtil.isNull( sport.getId(),"Sport","id",resources.getId());
        sport.copy(resources);
        sportRepository.save(sport);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            sportRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SportDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SportDto sport : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", sport.getName());
            map.put("描述", sport.getDescription());
            map.put("创建时间", sport.getCreateTime());
            map.put("更新时间", sport.getUpdateTime());
            map.put("图标", sport.getIcon());
            map.put("排序", sport.getSort());
            map.put("是否启用", sport.getEnabled());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}