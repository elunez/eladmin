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
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * sm.ms图床
 *
 * @author Zheng Jie
 * @date 2018-12-27
 */
@Data
@Entity
@Table(name = "tool_picture")
public class Picture implements Serializable {

    @Id
    @Column(name = "picture_id")
    @ApiModelProperty(value = "ID", hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "文件名")
    private String filename;

    @ApiModelProperty(value = "图片url")
    private String url;

    @ApiModelProperty(value = "图片大小")
    private String size;

    @ApiModelProperty(value = "图片高")
    private String height;

    @ApiModelProperty(value = "图片宽")
    private String width;

    @Column(name = "delete_url")
    @ApiModelProperty(value = "用于删除的url")
    private String delete;

    @ApiModelProperty(value = "创建者")
    private String username;

    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    /** 用于检测文件是否重复 */
    private String md5Code;

    @Override
    public String toString() {
        return "Picture{" +
                "filename='" + filename + '\'' +
                '}';
    }
}
