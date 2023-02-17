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
package me.zhengjie.fin.stock.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * @website https://eladmin.vip
 * @description /
 * @author zhangjc
 * @date 2023-02-07
 **/
@Entity
@Data
@Table(name = "stock")
public class Stock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "`stock_code`", unique = true, nullable = false)
    @NotBlank
    @ApiModelProperty(value = "stockCode")
    private String stockCode;

    @Column(name = "`stock_name`", unique = true, nullable = false)
    @NotBlank
    @ApiModelProperty(value = "stockName")
    private String stockName;

    @Column(name = "`hold_amount`")
    @ApiModelProperty(value = "holdAmount")
    private Float holdAmount;

    @Column(name = "`hold_share`", nullable = false)
    @NotNull
    @ApiModelProperty(value = "holdShare")
    private Float holdShare;

    @Column(name = "`current_price`")
    @ApiModelProperty(value = "currentPrice")
    private Float currentPrice;

    @Column(name = "`price_time`")
    @ApiModelProperty(value = "priceTime")
    private Timestamp priceTime;

    @Column(name = "`create_by`")
    @ApiModelProperty(value = "createBy")
    private String createBy;

    @Column(name = "`create_time`")
    @ApiModelProperty(value = "createTime")
    private Timestamp createTime;

    @Column(name = "`update_by`")
    @ApiModelProperty(value = "updateBy")
    private String updateBy;

    @Column(name = "`update_time`")
    @ApiModelProperty(value = "updateTime")
    private Timestamp updateTime;

    public void copy(Stock source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}