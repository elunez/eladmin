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
import me.zhengjie.utils.GenUtil;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Column Data Information
 * @author Zheng Jie
 * @date 2019-01-02
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "code_column")
public class ColumnInfo implements Serializable {

    @Id
    @Column(name = "column_id")
    @ApiModelProperty(value = "ID", hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "Table Name")
    private String tableName;

    @ApiModelProperty(value = "Database Column Name")
    private String columnName;

    @ApiModelProperty(value = "Database Column Type")
    private String columnType;

    @ApiModelProperty(value = "Database Column Key Type")
    private String keyType;

    @ApiModelProperty(value = "Extra Parameters for Column")
    private String extra;

    @ApiModelProperty(value = "Database Column Description")
    private String remark;

    @ApiModelProperty(value = "Required")
    private Boolean notNull;

    @ApiModelProperty(value = "Show in List")
    private Boolean listShow;

    @ApiModelProperty(value = "Show in Form")
    private Boolean formShow;

    @ApiModelProperty(value = "Form Type")
    private String formType;

    @ApiModelProperty(value = "Query Type 1:Fuzzy 2:Exact")
    private String queryType;

    @ApiModelProperty(value = "Dictionary Name")
    private String dictName;

    @ApiModelProperty(value = "Date Annotation")
    private String dateAnnotation;

    public ColumnInfo(String tableName, String columnName, Boolean notNull, String columnType, String remark, String keyType, String extra) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnType = columnType;
        this.keyType = keyType;
        this.extra = extra;
        this.notNull = notNull;
        if(GenUtil.PK.equalsIgnoreCase(keyType) && GenUtil.EXTRA.equalsIgnoreCase(extra)){
            this.notNull = false;
        }
        this.remark = remark;
        this.listShow = true;
        this.formShow = true;
    }
}
