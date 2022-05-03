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
package me.zhengjie.portfolio.tour.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.converter.StringListConverter;
import me.zhengjie.converter.StringMapConverter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
* @website https://el-admin.vip
* @description /
* @author smk
* @date 2022-05-03
**/
@Entity
@Data
@Table(name="m_tour")
public class MTour implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "name")
    private String name;

    @Column(name = "start_date",nullable = false)
    @NotNull
    @ApiModelProperty(value = "startDate")
    private Date startDate = new Date();

    @Column(name = "period",nullable = false)
    @NotNull
    @ApiModelProperty(value = "period")
    private Integer period;

    @Column(name = "location",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "location")
    private String location;

    @Column(name = "tour_code",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "tourCode")
    private String tourCode;

    @Column(name = "tour_type",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "tourType")
    private String tourType;

    @Column(name = "description")
    @ApiModelProperty(value = "description")
    private String description;

    @Column(name = "extra_tour_detail",nullable = false)
    @ApiModelProperty(value = "extraTourDetail")
    @Convert(converter = StringMapConverter.class)
    private HashMap<String, String> extraTourDetail;

    @Column(name = "extra_room_detail",nullable = false)
    @ApiModelProperty(value = "extraRoomDetail")
    @Convert(converter = StringMapConverter.class)
    private HashMap<String, String> extraRoomDetail;

    @Column(name = "images",nullable = false)
    @ApiModelProperty(value = "images")
    @Convert(converter = StringListConverter.class)
    private List<String> images;

    public void copy(MTour source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}