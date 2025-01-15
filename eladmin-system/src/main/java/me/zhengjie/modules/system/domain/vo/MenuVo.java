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
package me.zhengjie.modules.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 构建前端路由时用到
 * @author Zheng Jie
 * @date 2018-12-20
 */
@Data
public class MenuVo implements Serializable {

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "隐藏状态")
    private Boolean hidden;

    @ApiModelProperty(value = "重定向")
    private String redirect;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "总是显示")
    private Boolean alwaysShow;

    @ApiModelProperty(value = "元数据")
    private MenuMetaVo meta;

    @ApiModelProperty(value = "子路由")
    private List<MenuVo> children;
}
