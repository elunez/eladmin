package me.zhengjie.modules.pay.service.dto;

import lombok.Data;
import java.io.Serializable;


/**
* @author xpay
* @date 2019-06-02
*/
@Data
public class PayConfigDTO implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 支付宝图片
     */
    private String imgAlipay;

    /**
     * 微信图片
     */
    private String imgWechat;

    /**
     * QQ支付图片
     */
    private String imgQq;

    /**
     * 银联支付图片
     */
    private String imgUnionpay;

    /**
     * 用户ID
     */
    private Long uid;

    /**
     * 异步回调
     */
    private String notifyUrl;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 商户号
     */
    private String sysServiceProviderId;
}