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

import lombok.Data;
import me.zhengjie.annotation.Query;
import io.swagger.annotations.ApiModelProperty;

/**
* @website https://eladmin.vip
* @author Chanheng
* @date 2025-05-18
**/
@Data
public class CourtQueryCriteria{

    /** 精确 */
    @Query
    @ApiModelProperty(value = "id")
    private Long id;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "clubId")
    private Long clubId;

    /** 精确 */
    @Query
    @ApiModelProperty(value = "sportId")
    private Long sportId;
}