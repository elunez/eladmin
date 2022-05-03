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
package me.zhengjie.portfolio.room.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.converter.StringListConverter;
import me.zhengjie.converter.StringMapConverter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @website https://el-admin.vip
* @description /
* @author smk
* @date 2022-05-03
**/
@Entity
@Data
@Table(name="m_room")
public class MRoom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "type")
    @ApiModelProperty(value = "type")
    private String type;

    @Column(name = "size")
    @ApiModelProperty(value = "size")
    private String size;

    @Column(name = "air_conditional",nullable = false)
    @NotNull
    @ApiModelProperty(value = "airConditional")
    private Integer airConditional;

    @Column(name = "fan",nullable = false)
    @NotNull
    @ApiModelProperty(value = "fan")
    private Integer fan;

    @Column(name = "free_parking")
    @ApiModelProperty(value = "freeParking")
    private Integer freeParking;

    @Column(name = "description")
    @ApiModelProperty(value = "description")
    private String description;

    @Column(name = "bad",nullable = false)
    @NotNull
    @ApiModelProperty(value = "bad")
    private Integer bad;

    @Column(name = "free_breakfast")
    @ApiModelProperty(value = "freeBreakfast")
    private Integer freeBreakfast;

    @Column(name = "image",nullable = false)
    @ApiModelProperty(value = "image")
    @Convert(converter = StringListConverter.class)
    private List<String> image;

    @Column(name = "extra_information")
    @ApiModelProperty(value = "extraInformation")
    @Convert(converter = StringMapConverter.class)
    private HashMap<String, String> extraInformation;

    public void copy(MRoom source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}