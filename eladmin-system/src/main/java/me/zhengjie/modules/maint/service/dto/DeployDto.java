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

import cn.hutool.core.collection.CollectionUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseDTO;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
* @author zhanghouying
* @date 2019-08-24
*/
@Getter
@Setter
public class DeployDto extends BaseDTO implements Serializable {

	@ApiModelProperty(value = "ID")
    private String id;

	@ApiModelProperty(value = "应用")
	private AppDto app;

	@ApiModelProperty(value = "服务器")
	private Set<ServerDeployDto> deploys;

	@ApiModelProperty(value = "服务器名称")
	private String servers;

	@ApiModelProperty(value = "服务状态")
	private String status;

	public String getServers() {
		if(CollectionUtil.isNotEmpty(deploys)){
			return deploys.stream().map(ServerDeployDto::getName).collect(Collectors.joining(","));
		}
		return servers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DeployDto deployDto = (DeployDto) o;
		return Objects.equals(id, deployDto.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
