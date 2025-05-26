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

import com.srr.domain.EventOrganizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @website https://eladmin.vip
* @author Chanheng
* @date 2025-05-26
**/
@Repository
public interface EventOrganizerRepository extends JpaRepository<EventOrganizer, Long>, JpaSpecificationExecutor<EventOrganizer> {
    
    /**
     * Find event organizers by user id
     * @param userId the user id
     * @return list of event organizers
     */
    List<EventOrganizer> findByUserId(Long userId);
}
