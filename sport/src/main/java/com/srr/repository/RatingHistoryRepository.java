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

import com.srr.domain.RatingHistory;
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
public interface RatingHistoryRepository extends JpaRepository<RatingHistory, Long>, JpaSpecificationExecutor<RatingHistory> {

    List<RatingHistory> findByPlayerIdOrderByCreateTimeDesc(Long playerId);
}