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
package com.srr.dto;

import com.srr.enumeration.WaitListStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.zhengjie.annotation.Query;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Chanheng
 * @date 2025-05-26
 */
@Data
public class WaitListQueryCriteria {

    @Query
    private Long id;
    
    @Query
    @ApiModelProperty(value = "Event ID")
    private Long eventId;
    
    @Query
    @ApiModelProperty(value = "Player ID")
    private Long playerId;
    
    @Query
    @ApiModelProperty(value = "Status")
    private WaitListStatus status;
    
    @Query(type = Query.Type.BETWEEN)
    @ApiModelProperty(value = "Create time range")
    private List<Timestamp> createTime;
}
