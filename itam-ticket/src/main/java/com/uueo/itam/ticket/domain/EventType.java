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
package com.uueo.itam.ticket.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author esinhee
* @date 2021-01-20
**/
@Entity
@Data
@Table(name="itam_event_type")
public class EventType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id", hidden = true)
    private Integer id;

    @Column(name = "typename",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "事件类型名称")
    private String typename;

    @Column(name = "parentid")
    @ApiModelProperty(value = "parentid")
    private Integer parentid;

    @ApiModelProperty(value = "subCount", hidden = true)
    private Integer subCount = 0;

    @Column(name = "level")
    @ApiModelProperty(value = "层级路径")
    private String level;

    @Column(name = "seq")
    @ApiModelProperty(value = "层级")
    private Integer seq;

    @Column(name = "pinyin")
    @ApiModelProperty(value = "拼音")
    private String pinyin;

    @Column(name = "bz")
    @ApiModelProperty(value = "启用标识")
    private Integer bz;

    public void copy(EventType source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}