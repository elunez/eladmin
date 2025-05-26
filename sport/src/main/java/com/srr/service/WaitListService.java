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

import com.srr.domain.WaitList;
import com.srr.dto.WaitListDto;
import com.srr.dto.WaitListQueryCriteria;
import me.zhengjie.utils.PageResult;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Chanheng
 * @date 2025-05-26
 */
public interface WaitListService {

    /**
     * Add a player to the wait list
     * @param waitList Player wait list entry
     * @return Created wait list entry
     */
    WaitListDto create(WaitList waitList);

    /**
     * Update wait list entry
     * @param waitList Updated wait list entry
     */
    void update(WaitList waitList);

    /**
     * Delete wait list entry
     * @param id Wait list entry ID
     */
    void delete(Long id);
    
    /**
     * Delete multiple wait list entries
     * @param ids List of wait list entry IDs
     */
    void deleteAll(List<Long> ids);

    /**
     * Find a wait list entry by ID
     * @param id Wait list entry ID
     * @return Wait list entry
     */
    WaitListDto findById(Long id);
    
    /**
     * Find wait list entries by event ID
     * @param eventId Event ID
     * @return List of wait list entries
     */
    List<WaitListDto> findByEventId(Long eventId);
    
    /**
     * Find wait list entries by player ID
     * @param playerId Player ID
     * @return List of wait list entries
     */
    List<WaitListDto> findByPlayerId(Long playerId);
    
    /**
     * Find wait list entry for a specific player and event
     * @param eventId Event ID
     * @param playerId Player ID
     * @return Wait list entry
     */
    WaitListDto findByEventAndPlayer(Long eventId, Long playerId);
    
    /**
     * Promote a player from wait list to event participant
     * @param waitListId Wait list entry ID
     * @return True if promotion successful
     */
    boolean promoteToParticipant(Long waitListId);
    
    /**
     * Query wait list with criteria
     * @param criteria Query criteria
     * @param pageable Pagination information
     * @return Page result with wait list entries
     */
    PageResult<WaitListDto> queryAll(WaitListQueryCriteria criteria, Pageable pageable);
    
    /**
     * Query all wait list entries
     * @param criteria Query criteria
     * @return List of wait list entries
     */
    List<WaitListDto> queryAll(WaitListQueryCriteria criteria);
    
    /**
     * Export wait list data
     * @param queryAll All wait list entries
     * @param response HTTP response
     * @throws IOException If export fails
     */
    void download(List<WaitListDto> queryAll, HttpServletResponse response) throws IOException;
}
