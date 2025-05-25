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
package me.zhengjie.service;

import me.zhengjie.domain.vo.TradeVo;
import me.zhengjie.domain.AlipayConfig;

/**
 * @author Zheng Jie
 * @date 2018-12-31
 */
public interface AliPayService {

    /**
     * Query configuration
     * @return AlipayConfig
     */
    AlipayConfig find();

    /**
     * Update configuration
     * @param alipayConfig Alipay configuration
     * @return AlipayConfig
     */
    AlipayConfig config(AlipayConfig alipayConfig);

    /**
     * Handle transaction requests from PC
     * @param alipay Alipay configuration
     * @param trade transaction details
     * @return String
     * @throws Exception exception
     */
    String toPayAsPc(AlipayConfig alipay, TradeVo trade) throws Exception;

    /**
     * Handle transaction requests from mobile web
     * @param alipay Alipay configuration
     * @param trade transaction details
     * @return String
     * @throws Exception exception
     */
    String toPayAsWeb(AlipayConfig alipay, TradeVo trade) throws Exception;
}
