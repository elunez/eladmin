/*
*  Copyright 2019-2020 Zheng Jie
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
package com.uueo.itam.ticket.service.dto;

import lombok.Data;
import me.zhengjie.base.BaseDTO;

import java.io.Serializable;
import java.util.List;

/**
* @website https://el-admin.vip
* @description /
* @author esinhee
* @date 2021-01-20
**/
@Data
public class EventTypeDto extends BaseDTO implements Serializable {

    private Integer id;

    private List<EventTypeDto> children;

    /** 事件类型名称 */
    private String typename;

    private Integer parentid;

    /** 子项数 */
    private Integer subCount;

    /** 层级路径 */
    private String level;

    /** 层级 */
    private Integer seq;

    /** 拼音 */
    private String pinyin;

    /** 启用标识 */
    private Integer bz;

    public Boolean getHasChildren() {
        return subCount > 0;
    }

    public Boolean getLeaf() {
        return subCount <= 0;
    }
}