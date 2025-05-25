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
package me.zhengjie.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author Zheng Jie
* @date 2019-5-22
*/
@Data
public class SysLogErrorDto implements Serializable {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "Username")
    private String username;

    @ApiModelProperty(value = "Description")
    private String description;

    @ApiModelProperty(value = "Method")
    private String method;

    @ApiModelProperty(value = "Parameters")
    private String params;

    @ApiModelProperty(value = "Browser")
    private String browser;

    @ApiModelProperty(value = "Request IP")
    private String requestIp;

    @ApiModelProperty(value = "Address")
    private String address;

    @ApiModelProperty(value = "Creation time")
    private Timestamp createTime;
}