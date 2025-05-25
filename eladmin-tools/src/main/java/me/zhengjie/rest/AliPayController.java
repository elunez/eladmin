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
package me.zhengjie.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.annotation.rest.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.annotation.rest.AnonymousGetMapping;
import me.zhengjie.domain.vo.TradeVo;
import me.zhengjie.domain.AlipayConfig;
import me.zhengjie.domain.enums.AliPayStatusEnum;
import me.zhengjie.utils.AlipayUtils;
import me.zhengjie.service.AliPayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Zheng Jie
 * @date 2018-12-31
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aliPay")
@Api(tags = "Tools: Alipay Management")
public class AliPayController {

    private final AlipayUtils alipayUtils;
    private final AliPayService alipayService;

    @GetMapping
    public ResponseEntity<AlipayConfig> queryAliConfig() {
        return new ResponseEntity<>(alipayService.find(), HttpStatus.OK);
    }

    @Log("Configure Alipay")
    @ApiOperation("Configure Alipay")
    @PutMapping
    public ResponseEntity<Object> updateAliPayConfig(@Validated @RequestBody AlipayConfig alipayConfig) {
        alipayService.config(alipayConfig);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("Alipay PC Web Payment")
    @ApiOperation("PC Web Payment")
    @PostMapping(value = "/toPayAsPC")
    public ResponseEntity<String> toPayAsPc(@Validated @RequestBody TradeVo trade) throws Exception {
        AlipayConfig aliPay = alipayService.find();
        trade.setOutTradeNo(alipayUtils.getOrderCode());
        String payUrl = alipayService.toPayAsPc(aliPay, trade);
        return ResponseEntity.ok(payUrl);
    }

    @Log("Alipay Mobile Web Payment")
    @ApiOperation("Mobile Web Payment")
    @PostMapping(value = "/toPayAsWeb")
    public ResponseEntity<String> toPayAsWeb(@Validated @RequestBody TradeVo trade) throws Exception {
        AlipayConfig alipay = alipayService.find();
        trade.setOutTradeNo(alipayUtils.getOrderCode());
        String payUrl = alipayService.toPayAsWeb(alipay, trade);
        return ResponseEntity.ok(payUrl);
    }

    @ApiIgnore
    @AnonymousGetMapping("/return")
    @ApiOperation("Redirect link after payment")
    public ResponseEntity<String> returnPage(HttpServletRequest request, HttpServletResponse response) {
        AlipayConfig alipay = alipayService.find();
        response.setContentType("text/html;charset=" + alipay.getCharset());
        // Content signature verification to prevent hackers from tampering with parameters
        if (alipayUtils.rsaCheck(request, alipay)) {
            // Merchant order number
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // Alipay transaction number
            String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            System.out.println("Merchant order number" + outTradeNo + "  " + "Third-party transaction number" + tradeNo);

            // Return data as needed by business, here always return OK
            return new ResponseEntity<>("payment successful", HttpStatus.OK);
        } else {
            // Return data as needed by business
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiIgnore
    @RequestMapping("/notify")
    @AnonymousAccess
    @ApiOperation("Alipay async notification (requires public network access), receive async notification, check if app_id, out_trade_no, and total_amount in the notification match the request, handle business logic according to trade_status")
    public ResponseEntity<Object> notify(HttpServletRequest request) {
        AlipayConfig alipay = alipayService.find();
        Map<String, String[]> parameterMap = request.getParameterMap();
        // Content signature verification to prevent hackers from tampering with parameters
        if (alipayUtils.rsaCheck(request, alipay)) {
            // Transaction status
            String tradeStatus = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // Merchant order number
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // Alipay transaction number
            String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // Payment amount
            String totalAmount = new String(request.getParameter("total_amount").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // Verification
            if (tradeStatus.equals(AliPayStatusEnum.SUCCESS.getValue()) || tradeStatus.equals(AliPayStatusEnum.FINISHED.getValue())) {
                // After verification, handle order as needed by business
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
