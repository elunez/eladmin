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
package me.zhengjie.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import me.zhengjie.domain.AlipayConfig;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝工具类
 * @author zhengjie
 * @date 2018/09/30 14:04:35
 */
@Component
public class AlipayUtils {

    /**
     * 生成订单号
     * @return String
     */
    public String getOrderCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int a = (int)(Math.random() * 9000.0D) + 1000;
        System.out.println(a);
        Date date = new Date();
        String str = sdf.format(date);
        String[] split = str.split("-");
        String s = split[0] + split[1] + split[2];
        String[] split1 = s.split(" ");
        String s1 = split1[0] + split1[1];
        String[] split2 = s1.split(":");
        return split2[0] + split2[1] + split2[2] + a;
    }

    /**
     * 校验签名
     * @param request HttpServletRequest
     * @param alipay 阿里云配置
     * @return boolean
     */
    public boolean rsaCheck(HttpServletRequest request, AlipayConfig alipay){

        // 获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<>(1);
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        try {
            return AlipaySignature.rsaCheckV1(params,
                    alipay.getPublicKey(),
                    alipay.getCharset(),
                    alipay.getSignType());
        } catch (AlipayApiException e) {
            return false;
        }
    }
}
