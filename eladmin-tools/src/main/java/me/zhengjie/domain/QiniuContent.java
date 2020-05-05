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
package me.zhengjie.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 上传成功后，存储结果
 * @author Zheng Jie
 * @date 2018-12-31
 */
@Data
@Entity
@Table(name = "tool_qiniu_content")
public class QiniuContent implements Serializable {

    @Id
    @Column(name = "content_id")
    @ApiModelProperty(value = "ID", hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @ApiModelProperty(value = "文件名")
    private String key;

    @ApiModelProperty(value = "空间名")
    private String bucket;

    @ApiModelProperty(value = "大小")
    private String size;

    @ApiModelProperty(value = "文件地址")
    private String url;

    @ApiModelProperty(value = "文件类型")
    private String suffix;

    @ApiModelProperty(value = "空间类型：公开/私有")
    private String type = "公开";

    @UpdateTimestamp
    @ApiModelProperty(value = "创建或更新时间")
    @Column(name = "update_time")
    private Timestamp updateTime;
}
