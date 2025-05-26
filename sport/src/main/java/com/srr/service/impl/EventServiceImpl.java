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
import com.srr.domain.Player;
import com.srr.domain.Team;
import com.srr.domain.TeamPlayer;
import com.srr.enumeration.EventStatus;
import com.srr.enumeration.Format;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.srr.repository.EventRepository;
import com.srr.service.EventService;
import com.srr.dto.EventDto;
import com.srr.dto.EventQueryCriteria;
import com.srr.dto.mapstruct.EventMapper;
import com.srr.dto.JoinEventDto;
import com.srr.repository.TeamPlayerRepository;
import com.srr.repository.TeamRepository;
import me.zhengjie.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import me.zhengjie.utils.PageResult;

/**
 * @author Chanheng
 * @website https://eladmin.vip
 * @description 服务实现
 * @date 2025-05-18
 **/
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final TeamRepository teamRepository;
    private final TeamPlayerRepository teamPlayerRepository;

    @Override
    public PageResult<EventDto> queryAll(EventQueryCriteria criteria, Pageable pageable) {
        Page<Event> page = eventRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(eventMapper::toDto));
    }

    @Override
    public List<EventDto> queryAll(EventQueryCriteria criteria) {
        return eventMapper.toDto(eventRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public EventDto findById(Long id) {
        Event event = eventRepository.findById(id).orElseGet(Event::new);
        ValidationUtil.isNull(event.getId(), "Event", "id", id);
        return eventMapper.toDto(event);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EventDto create(Event resources) {
        resources.setStatus(EventStatus.DRAFT);
        final var result = eventRepository.save(resources);
        return eventMapper.toDto(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EventDto update(Event resources) {
        Event event = eventRepository.findById(resources.getId()).orElseGet(Event::new);
        ValidationUtil.isNull(event.getId(), "Event", "id", resources.getId());
        event.copy(resources);
        final var result = eventRepository.save(event);
        return eventMapper.toDto(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EventDto updateStatus(Long id, EventStatus status) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Event.class, "id", Long.valueOf(id)));

        // Only update the status field
        event.setStatus(status);
        if (status == EventStatus.CHECK_IN) {
            event.setCheckInAt(Timestamp.from(Instant.now()));
        }

        final var result = eventRepository.save(event);
        return eventMapper.toDto(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EventDto joinEvent(JoinEventDto joinEventDto) {
        // Find the event
        Event event = eventRepository.findById(joinEventDto.getEventId())
                .orElseThrow(() -> new EntityNotFoundException(Event.class, "id", Long.valueOf(joinEventDto.getEventId())));
        
        // Check if event allows joining
        if (event.getStatus() != EventStatus.OPEN) {
            throw new BadRequestException("Event is not open for joining");
        }
        
        // Check if event is full and handle waitlist
        boolean isWaitList = joinEventDto.getJoinWaitList() != null && joinEventDto.getJoinWaitList();
        if (event.getMaxParticipants() != null && 
            event.getCurrentParticipants() >= event.getMaxParticipants() && 
            !isWaitList) {
            if (!event.isAllowWaitList()) {
                throw new BadRequestException("Event is full and does not allow waitlist");
            }
            // Set joinWaitList to true if event is full and waitlist is allowed
            isWaitList = true;
        }
        
        // Handle team-related logic
        if (joinEventDto.getTeamId() != null) {
            // Add player to existing team
            Team team = teamRepository.findById(joinEventDto.getTeamId())
                    .orElseThrow(() -> new EntityNotFoundException(Team.class, "id", Long.valueOf(joinEventDto.getTeamId())));
            
            // Verify team belongs to this event
            if (!team.getEvent().getId().equals(event.getId())) {
                throw new BadRequestException("Team does not belong to this event");
            }
            
            // Check if player is already in the team
            if (teamPlayerRepository.existsByTeamIdAndPlayerId(team.getId(), joinEventDto.getPlayerId())) {
                throw new BadRequestException("Player is already in this team");
            }
            
            // Check if team is full
            if (team.getTeamSize() == team.getTeamPlayers().size()) {
                throw new BadRequestException("Team is already full");
            }
            
            // Add player to team
            TeamPlayer teamPlayer = new TeamPlayer();
            teamPlayer.setTeam(team);
            Player player = new Player();
            player.setId(joinEventDto.getPlayerId());
            teamPlayer.setPlayer(player);
            teamPlayer.setCheckedIn(false);
            teamPlayerRepository.save(teamPlayer);
        } else {
            // Create new team for the player if needed or add as individual participant
            if (event.getFormat() == Format.SINGLE) {
                // For single format, create a "virtual" team with just one player
                Team team = new Team();
                team.setEvent(event);
                team.setName("Player " + joinEventDto.getPlayerId());
                team.setTeamSize(1);
                Team savedTeam = teamRepository.save(team);
                
                // Add player to the team
                TeamPlayer teamPlayer = new TeamPlayer();
                teamPlayer.setTeam(savedTeam);
                Player player = new Player();
                player.setId(joinEventDto.getPlayerId());
                teamPlayer.setPlayer(player);
                teamPlayer.setCheckedIn(false);
                teamPlayerRepository.save(teamPlayer);
            } else if (event.getFormat() == Format.DOUBLE || event.getFormat() == Format.TEAM) {
                // For doubles/team format, create a new team
                Team team = new Team();
                team.setEvent(event);
                team.setName("New Team");
                
                // Set team size based on format
                if (event.getFormat() == Format.DOUBLE) {
                    team.setTeamSize(2);
                } else {
                    // Default team size of 4 for TEAM format, can be adjusted as needed
                    team.setTeamSize(4);
                }
                
                Team savedTeam = teamRepository.save(team);
                
                // Add player as the first member of the team
                TeamPlayer teamPlayer = new TeamPlayer();
                teamPlayer.setTeam(savedTeam);
                Player player = new Player();
                player.setId(joinEventDto.getPlayerId());
                teamPlayer.setPlayer(player);
                teamPlayer.setCheckedIn(false);
                teamPlayerRepository.save(teamPlayer);
            }
        }
        
        // Update participant count if not joining waitlist
        if (!isWaitList) {
            event.setCurrentParticipants(event.getCurrentParticipants() + 1);
        }
        
        // Save and return updated event
        final var result = eventRepository.save(event);
        return eventMapper.toDto(result);
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
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("名称", event.getName());
            map.put("描述", event.getDescription());
            map.put("SINGLE, DOUBLE", event.getFormat());
            map.put("位置", event.getLocation());
            map.put("图片", event.getImage());
            map.put("创建时间", event.getCreateTime());
            map.put("更新时间", event.getUpdateTime());
            map.put("排序", event.getSort());
            map.put("是否启用", event.getEnabled());
            map.put("时间", event.getEventTime());
            map.put(" clubId", event.getClubId());
            map.put(" createBy", event.getCreateBy());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}