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
package com.srr.repository;

import com.srr.domain.WaitList;
import com.srr.enumeration.WaitListStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author Chanheng
 * @date 2025-05-26
 */
public interface WaitListRepository extends JpaRepository<WaitList, Long>, JpaSpecificationExecutor<WaitList> {
    
    /**
     * Find all wait list entries for an event
     * @param eventId The event ID
     * @return List of wait list entries
     */
    List<WaitList> findByEventId(Long eventId);
    
    /**
     * Find all wait list entries for a player
     * @param playerId The player ID
     * @return List of wait list entries
     */
    List<WaitList> findByPlayerId(Long playerId);
    
    /**
     * Find wait list entry for a specific player and event
     * @param eventId The event ID
     * @param playerId The player ID
     * @return WaitList entry if exists
     */
    WaitList findByEventIdAndPlayerId(Long eventId, Long playerId);
    
    /**
     * Find wait list entries by status
     * @param status The status (WAITING, PROMOTED, CANCELLED, EXPIRED)
     * @return List of wait list entries
     */
    List<WaitList> findByStatus(WaitListStatus status);
    
    /**
     * Count wait list entries for an event
     * @param eventId The event ID
     * @return Count of wait list entries
     */
    long countByEventId(Long eventId);
}
