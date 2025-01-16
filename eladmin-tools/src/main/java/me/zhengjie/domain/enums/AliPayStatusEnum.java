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
package me.zhengjie.domain.enums;

/**
 * 支付状态
 * @author zhengjie
 * @date 2018/08/01 16:45:43
 */
public enum  AliPayStatusEnum {

    /** 交易成功 */
    FINISHED("TRADE_FINISHED"),

    /** 支付成功 */
    SUCCESS("TRADE_SUCCESS"),

    /** 交易创建 */
    BUYER_PAY("WAIT_BUYER_PAY"),

    /** 交易关闭 */
    CLOSED("TRADE_CLOSED");

    private final String value;

    AliPayStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
