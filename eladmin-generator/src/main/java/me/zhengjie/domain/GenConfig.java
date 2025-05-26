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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Code Generation Configuration
 * @author Zheng Jie
 * @date 2019-01-03
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "code_config")
public class GenConfig implements Serializable {

    public GenConfig(String tableName) {
        this.tableName = tableName;
    }

    @Id
    @Column(name = "config_id")
    @ApiModelProperty(value = "ID", hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "Table Name")
    private String tableName;

    @ApiModelProperty(value = "API Alias")
    private String apiAlias;

    @NotBlank
    @ApiModelProperty(value = "Package Path")
    private String pack;

    @NotBlank
    @ApiModelProperty(value = "Module Name")
    private String moduleName;

    @NotBlank
    @ApiModelProperty(value = "Frontend File Path")
    private String path;

    @ApiModelProperty(value = "Frontend API Path")
    private String apiPath;

    @ApiModelProperty(value = "Author")
    private String author;

    @ApiModelProperty(value = "Table Prefix")
    private String prefix;

    @ApiModelProperty(value = "Overwrite")
    private Boolean cover = false;
}
