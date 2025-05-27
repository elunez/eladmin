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

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
* @website https://eladmin.vip
* @description /
* @author Chanheng
* @date 2025-05-25
**/
@Data
public class TeamDto implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "Event")
    private Long eventId;

    @ApiModelProperty(value = "Match Group")
    private Long matchGroupId;

    @ApiModelProperty(value = "Name")
    private String name;

    @ApiModelProperty(value = "Team size")
    private Integer teamSize;
    
    @ApiModelProperty(value = "Average team score based on player scores")
    private Double averageScore;

    @ApiModelProperty(value = "Team players")
    private List<TeamPlayerDto> teamPlayers;
}
