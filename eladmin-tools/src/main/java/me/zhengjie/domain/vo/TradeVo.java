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
package me.zhengjie.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Transaction details, should be stored in the database as needed. Here it is stored in the database for temporary testing only
 * @author Zheng Jie
 * @date 2018-12-31
 */
@Data
public class TradeVo {

    @NotBlank
    @ApiModelProperty(value = "Product description")
    private String body;

    @NotBlank
    @ApiModelProperty(value = "Product name")
    private String subject;

    @ApiModelProperty(value = "Merchant order number", hidden = true)
    private String outTradeNo;

    @ApiModelProperty(value = "Third-party order number", hidden = true)
    private String tradeNo;

    @NotBlank
    @ApiModelProperty(value = "Price")
    private String totalAmount;

    @ApiModelProperty(value = "Order status, paid, unpaid, invalid", hidden = true)
    private String state;

    @ApiModelProperty(value = "Creation time", hidden = true)
    private Timestamp createTime;

    @ApiModelProperty(value = "Invalid time", hidden = true)
    private Date cancelTime;
}
