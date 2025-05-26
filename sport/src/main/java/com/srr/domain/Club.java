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
package com.srr.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author Chanheng
* @date 2025-05-18
**/
@Entity
@Data
@Table(name="club")
public class Club implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id", hidden = true)
    private Long id;

    @Column(name = "name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "Name")
    private String name;

    @Column(name = "description")
    @ApiModelProperty(value = "Description")
    private String description;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "Creation time", hidden = true)
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "Update time", hidden = true)
    private Timestamp updateTime;

    @Column(name = "icon")
    @ApiModelProperty(value = "Icon")
    private String icon;

    @Column(name = "sort")
    @ApiModelProperty(value = "Sort")
    private Integer sort;

    @Column(name = "enabled")
    @ApiModelProperty(value = "Enabled")
    private Boolean enabled;

    @Column(name = "location")
    @ApiModelProperty(value = "Location")
    private String location;

    @Column(name = "longitude")
    @ApiModelProperty(value = "Longitude")
    private Double longitude;

    @Column(name = "latitude")
    @ApiModelProperty(value = "Latitude")
    private Double latitude;

    public void copy(Club source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
