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
package me.zhengjie.modules.maint.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Data
public class DeployHistoryDto implements Serializable {

	@ApiModelProperty(value = "ID")
    private String id;

	@ApiModelProperty(value = "应用名称")
    private String appName;

	@ApiModelProperty(value = "部署IP")
    private String ip;

	@ApiModelProperty(value = "部署时间")
	private Timestamp deployDate;

	@ApiModelProperty(value = "部署人员")
	private String deployUser;

	@ApiModelProperty(value = "部署编号")
	private Long deployId;
}
