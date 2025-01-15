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
package me.zhengjie.modules.system.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zhengjie.annotation.Query;
import java.sql.Timestamp;
import java.util.List;

/**
* @author Zheng Jie
* @date 2019-6-4 14:49:34
*/
@Data
@NoArgsConstructor
public class JobQueryCriteria {

    @ApiModelProperty(value = "岗位名称")
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query
    @ApiModelProperty(value = "岗位状态")
    private Boolean enabled;

    @ApiModelProperty(value = "创建时间")
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}