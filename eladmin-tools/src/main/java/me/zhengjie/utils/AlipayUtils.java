package me.zhengjie.utils;

import cn.hutool.core.util.StrUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import me.zhengjie.domain.AlipayConfig;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
     * @return
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
        String s2 = split2[0] + split2[1] + split2[2] + a;
        return s2;
    }

    /**
     * 校验签名
     * @param request
     * @return
     */
    public boolean rsaCheck(HttpServletRequest request, AlipayConfig alipay){

        /**
         *  获取支付宝POST过来反馈信息
         */
        Map<String,String> params = new HashMap<>(1);
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        try {
            boolean verifyResult = AlipaySignature.rsaCheckV1(params,
                    alipay.getPublicKey(),
                    alipay.getCharset(),
                    alipay.getSignType());
            return verifyResult;
        } catch (AlipayApiException e) {
            return false;
        }
    }

    public boolean isEmpty(String str){
        return StrUtil.isEmpty(str);
    }
}
