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
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author esinhee
* @date 2021-01-19
**/
@Data
public class EventDto implements Serializable {

    /** 事件ID */
    private Integer eid;

    /** 事件类型 */
    private Integer etid;

    /** 重要程度 */
    private Integer grade;

    /** 详情描述 */
    private String content;

    /** 反馈科室 */
    private String kid;

    /** 反馈人 */
    private String reportid;

    /** 反馈时间 */
    private Timestamp reporttime;

    /** 记录人 */
    private Integer recorderid;

    /** 记录时间 */
    private Timestamp recordetime;

    /** 处理人 */
    private Integer dealerid;

    /** 处理时间 */
    private Timestamp dealtime;

    /** 最后更新时间 */
    private Timestamp updatetime;

    /** 处理标识 */
    private Integer dealflg;

    /** 关联事件ID */
    private Integer relatedid;

    /** 状态标识 */
    private Integer bz;
}