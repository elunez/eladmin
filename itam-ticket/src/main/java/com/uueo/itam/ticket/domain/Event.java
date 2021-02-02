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
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author esinhee
* @date 2021-01-19
**/
@Entity
@Data
@Table(name="itam_event")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eid")
    @ApiModelProperty(value = "事件ID")
    private Integer eid;

    @Column(name = "etid")
    @ApiModelProperty(value = "事件类型")
    private Integer etid;

    @Column(name = "grade")
    @ApiModelProperty(value = "重要程度")
    private Integer grade;

    @Column(name = "content")
    @ApiModelProperty(value = "详情描述")
    private String content;

    @Column(name = "kid")
    @ApiModelProperty(value = "反馈科室")
    private String kid;

    @Column(name = "reportid")
    @ApiModelProperty(value = "反馈人")
    private String reportid;

    @Column(name = "reporttime")
    @ApiModelProperty(value = "反馈时间")
    private Timestamp reporttime;

    @Column(name = "recorderid")
    @ApiModelProperty(value = "记录人")
    private Integer recorderid;

    @Column(name = "recordetime")
    @ApiModelProperty(value = "记录时间")
    private Timestamp recordetime;

    @Column(name = "dealerid")
    @ApiModelProperty(value = "处理人")
    private Integer dealerid;

    @Column(name = "dealtime")
    @ApiModelProperty(value = "处理时间")
    private Timestamp dealtime;

    @Column(name = "updatetime")
    @ApiModelProperty(value = "最后更新时间")
    private Timestamp updatetime;

    @Column(name = "dealflg")
    @ApiModelProperty(value = "处理标识")
    private Integer dealflg;

    @Column(name = "relatedid")
    @ApiModelProperty(value = "关联事件ID")
    private Integer relatedid;

    @Column(name = "bz")
    @ApiModelProperty(value = "状态标识")
    private Integer bz;

    public void copy(Event source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}