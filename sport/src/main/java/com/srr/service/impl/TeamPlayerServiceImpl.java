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

import com.srr.domain.Team;
import com.srr.domain.TeamPlayer;
import com.srr.dto.TeamPlayerDto;
import com.srr.dto.TeamPlayerReassignDto;
import com.srr.dto.mapstruct.TeamPlayerMapper;
import com.srr.repository.TeamPlayerRepository;
import com.srr.repository.TeamRepository;
import com.srr.service.TeamPlayerService;
import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Chanheng
 * @website https://eladmin.vip
 * @date 2025-05-26
 **/
@Service
@RequiredArgsConstructor
public class TeamPlayerServiceImpl implements TeamPlayerService {

    private final TeamPlayerRepository teamPlayerRepository;
    private final TeamRepository teamRepository;
    private final TeamPlayerMapper teamPlayerMapper;

    @Override
    @Transactional(readOnly = true)
    public TeamPlayerDto findById(Long id) {
        TeamPlayer teamPlayer = teamPlayerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TeamPlayer.class, "id", id.toString()));
        return teamPlayerMapper.toDto(teamPlayer);
    }

    @Override
    @Transactional
    public TeamPlayerDto checkIn(Long id) {
        TeamPlayer teamPlayer = teamPlayerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TeamPlayer.class, "id", id.toString()));
        
        if (teamPlayer.isCheckedIn()) {
            throw new BadRequestException("Player is already checked in");
        }
        
        teamPlayer.setCheckedIn(true);
        teamPlayerRepository.save(teamPlayer);
        
        return teamPlayerMapper.toDto(teamPlayer);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TeamPlayerDto> findByEventId(Long eventId) {
        List<TeamPlayer> teamPlayers = teamPlayerRepository.findByEventId(eventId);
        return teamPlayers.stream()
                .map(teamPlayerMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public TeamPlayerDto reassignPlayer(TeamPlayerReassignDto dto) {
        // Find the team player to reassign
        TeamPlayer teamPlayer = teamPlayerRepository.findById(dto.getTeamPlayerId())
                .orElseThrow(() -> new EntityNotFoundException(TeamPlayer.class, "id", dto.getTeamPlayerId().toString()));
        
        // Find the target team
        Team targetTeam = teamRepository.findById(dto.getTargetTeamId())
                .orElseThrow(() -> new EntityNotFoundException(Team.class, "id", dto.getTargetTeamId().toString()));
        
        // Store the original team for potential deletion
        Team originalTeam = teamPlayer.getTeam();
        
        // Check if target team is in the same event as the original team
        if (!originalTeam.getEvent().getId().equals(targetTeam.getEvent().getId())) {
            throw new BadRequestException("Cannot reassign player to a team in a different event");
        }
        
        // Check if the target team is full
        if (targetTeam.getTeamPlayers().size() >= targetTeam.getTeamSize()) {
            throw new BadRequestException("Target team is already full");
        }
        
        // Reassign the player to the new team
        teamPlayer.setTeam(targetTeam);
        teamPlayerRepository.save(teamPlayer);
        
        // Check if original team is now empty, and delete if it is
        if (originalTeam.getTeamPlayers().stream()
                .noneMatch(tp -> !tp.getId().equals(teamPlayer.getId()))) {
            teamRepository.delete(originalTeam);
        }
        
        return teamPlayerMapper.toDto(teamPlayer);
    }
}
