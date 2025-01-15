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
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseDTO;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Getter
@Setter
public class DeptDto extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @ApiModelProperty(value = "排序")
    private Integer deptSort;

    @ApiModelProperty(value = "子部门")
    private List<DeptDto> children;

    @ApiModelProperty(value = "上级部门")
    private Long pid;

    @ApiModelProperty(value = "子部门数量", hidden = true)
    private Integer subCount;

    @ApiModelProperty(value = "是否有子节点")
    public Boolean getHasChildren() {
        return subCount > 0;
    }

    @ApiModelProperty(value = "是否为叶子")
    public Boolean getLeaf() {
        return subCount <= 0;
    }

    @ApiModelProperty(value = "部门全名")
    public String getLabel() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeptDto deptDto = (DeptDto) o;
        return Objects.equals(id, deptDto.id) &&
                Objects.equals(name, deptDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}