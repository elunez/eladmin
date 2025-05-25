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
package me.zhengjie.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
@Entity
@Getter
@Setter
@Table(name = "sys_log")
@NoArgsConstructor
public class SysLog implements Serializable {

    @Id
    @Column(name = "log_id")
    @ApiModelProperty(value = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "Operator User")
    private String username;

    @ApiModelProperty(value = "Description")
    private String description;

    @ApiModelProperty(value = "Method Name")
    private String method;

    @ApiModelProperty(value = "Parameters")
    private String params;

    @ApiModelProperty(value = "Log Type")
    private String logType;

    @ApiModelProperty(value = "Request IP")
    private String requestIp;

    @ApiModelProperty(value = "Address")
    private String address;

    @ApiModelProperty(value = "Browser")
    private String browser;

    @ApiModelProperty(value = "Request Duration")
    private Long time;

    @ApiModelProperty(value = "Exception Details")
    private byte[] exceptionDetail;

    /** Creation Date */
    @CreationTimestamp
    @ApiModelProperty(value = "Creation Date: yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    public SysLog(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}
