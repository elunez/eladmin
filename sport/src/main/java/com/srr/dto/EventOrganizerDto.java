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
import java.sql.Timestamp;

/**
* @website https://eladmin.vip
* @description Event organizer data transfer object
* @author Chanheng
* @date 2025-05-26
**/
@Data
public class EventOrganizerDto implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "Description")
    private String description;

    @ApiModelProperty(value = "Creation time")
    private Timestamp createTime;

    @ApiModelProperty(value = "Update time")
    private Timestamp updateTime;

    @ApiModelProperty(value = "User ID")
    private Long userId;
}
