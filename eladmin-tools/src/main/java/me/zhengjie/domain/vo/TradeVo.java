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
package me.zhengjie.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 交易详情，按需应该存入数据库，这里存入数据库，仅供临时测试
 * @author Zheng Jie
 * @date 2018-12-31
 */
@Data
public class TradeVo {

    /** （必填）商品描述 */
    @NotBlank
    private String body;

    /** （必填）商品名称 */
    @NotBlank
    private String subject;

    /** （必填）商户订单号，应该由后台生成 */
    @ApiModelProperty(hidden = true)
    private String outTradeNo;

    /** （必填）第三方订单号 */
    @ApiModelProperty(hidden = true)
    private String tradeNo;

    /** （必填）价格 */
    @NotBlank
    private String totalAmount;

    /** 订单状态,已支付，未支付，作废 */
    @ApiModelProperty(hidden = true)
    private String state;

    /** 创建时间，存入数据库时需要 */
    @ApiModelProperty(hidden = true)
    private Timestamp createTime;

    /** 作废时间，存入数据库时需要 */
    @ApiModelProperty(hidden = true)
    private Date cancelTime;
}
