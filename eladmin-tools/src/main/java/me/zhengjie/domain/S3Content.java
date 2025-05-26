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

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Storage result after successful upload
 * @author Zheng Jie
 * @date 2018-12-31
 */
@Data
@Entity
@Table(name = "tool_s3_content")
public class S3Content implements Serializable {

    @Id
    @Column(name = "content_id")
    @ApiModelProperty(value = "ID", hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @ApiModelProperty(value = "File name")
    private String key;

    @ApiModelProperty(value = "Bucket name")
    private String bucket;

    @ApiModelProperty(value = "Size")
    private String size;

    @ApiModelProperty(value = "File address")
    private String url;

    @ApiModelProperty(value = "File type")
    private String suffix;

    @ApiModelProperty(value = "Space type: public/private")
    private String type = "public";

    @UpdateTimestamp
    @ApiModelProperty(value = "Creation or update time")
    @Column(name = "update_time")
    private Timestamp updateTime;
}
