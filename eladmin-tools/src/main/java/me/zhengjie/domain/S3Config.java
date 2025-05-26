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
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * S3 Cloud Object Storage Configuration Class
 * @author Zheng Jie
 * @date 2018-12-31
 */
@Data
@Entity
@Table(name = "tool_s3_config")
public class S3Config implements Serializable {

    @Id
    @Column(name = "config_id")
    @ApiModelProperty(value = "ID")
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "Access Key")
    private String accessKey;

    @NotBlank
    @ApiModelProperty(value = "Secret Key")
    private String secretKey;

    @NotBlank
    @ApiModelProperty(value = "Storage space name used as the unique Bucket identifier")
    private String bucket;

    /**
     * Zone represents the correspondence between data center and region
     * East China	Zone.zone0()
     * North China	Zone.zone1()
     * South China	Zone.zone2()
     * North America	Zone.zoneNa0()
     * Southeast Asia	Zone.zoneAs0()
     */
    @NotBlank
    @ApiModelProperty(value = "Zone represents the correspondence between data center and region")
    private String zone;

    @NotBlank
    @ApiModelProperty(value = "External domain, customizable, needs to be bound in S3 Cloud")
    private String host;

    @ApiModelProperty(value = "Space type: public/private")
    private String type = "public";
}
