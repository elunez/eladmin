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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.srr.enumeration.WaitListStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Chanheng
 * @date 2025-05-26
 * @description Wait list DTO for transferring wait list data
 */
@Getter
@Setter
public class WaitListDto implements Serializable {
    
    @ApiModelProperty(value = "id")
    private Long id;
    
    @ApiModelProperty(value = "Event ID")
    private Long eventId;
    
    @ApiModelProperty(value = "Player ID")
    private Long playerId;
    
    @ApiModelProperty(value = "Notes")
    private String notes;
    
    @ApiModelProperty(value = "Status (WAITING, PROMOTED, CANCELLED, EXPIRED)")
    private WaitListStatus status;
    
    @ApiModelProperty(value = "Creation time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;
    
    @ApiModelProperty(value = "Update time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateTime;
    
    // Additional relationships can be added if needed
    @ApiModelProperty(value = "Player")
    private PlayerDto player;
    
    @ApiModelProperty(value = "Event")
    private EventDto event;
}
