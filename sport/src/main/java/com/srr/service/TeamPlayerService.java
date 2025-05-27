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
package com.srr.service;

import com.srr.domain.TeamPlayer;
import com.srr.dto.TeamPlayerDto;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageResult;

/**
 * @author Chanheng
 * @website https://eladmin.vip
 * @description Service interface for TeamPlayer
 * @date 2025-05-26
 **/
public interface TeamPlayerService {

    /**
     * Get a specific TeamPlayer by ID
     * @param id TeamPlayer ID
     * @return TeamPlayerDto
     */
    TeamPlayerDto findById(Long id);

    /**
     * Check in a player for an event
     * @param id TeamPlayer ID
     * @return The updated TeamPlayerDto
     */
    TeamPlayerDto checkIn(Long id);
    
    /**
     * Find TeamPlayer by teamId and playerId
     * @param teamId the team ID
     * @param playerId the player ID
     * @return TeamPlayerDto if found, null otherwise
     */
    TeamPlayerDto findByTeamIdAndPlayerId(Long teamId, Long playerId);
}
