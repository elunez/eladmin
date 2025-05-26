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

import com.srr.domain.Player;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.srr.repository.PlayerRepository;
import com.srr.service.PlayerService;
import com.srr.dto.PlayerDto;
import com.srr.dto.PlayerQueryCriteria;
import com.srr.dto.mapstruct.PlayerMapper;
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
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Override
    public PageResult<PlayerDto> queryAll(PlayerQueryCriteria criteria, Pageable pageable){
        Page<Player> page = playerRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(playerMapper::toDto));
    }

    @Override
    public List<PlayerDto> queryAll(PlayerQueryCriteria criteria){
        return playerMapper.toDto(playerRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public PlayerDto findById(Long id) {
        Player player = playerRepository.findById(id).orElseGet(Player::new);
        ValidationUtil.isNull(player.getId(),"Player","id",id);
        return playerMapper.toDto(player);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Player resources) {
        playerRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Player resources) {
        Player player = playerRepository.findById(resources.getId()).orElseGet(Player::new);
        ValidationUtil.isNull( player.getId(),"Player","id",resources.getId());
        player.copy(resources);
        playerRepository.save(player);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            playerRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<PlayerDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PlayerDto player : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", player.getName());
            map.put("描述", player.getDescription());
            map.put("纬度", player.getLatitude());
            map.put("经度", player.getLongitude());
            map.put("图片", player.getProfileImage());
            map.put("创建时间", player.getCreateTime());
            map.put("更新时间", player.getUpdateTime());
            map.put("评分", player.getRateScore());
            map.put(" userId",  player.getUserId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}