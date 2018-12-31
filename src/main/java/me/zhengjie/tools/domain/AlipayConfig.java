package me.zhengjie.tools.domain;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 支付宝配置类
 * @author jie
 * @date 2018-12-31
 */
@Data
@Entity
@Table(name = "alipay_config")
public class AlipayConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 应用ID,APPID，收款账号既是APPID对应支付宝账号
     */
    @NotBlank
    private String appID;

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    @NotBlank
    @Column(length = 2000)
    private String privateKey;

    /**
     * 支付宝公钥
     */
    @NotBlank
    @Column(length = 2000)
    private String publicKey;

    /**
     * 签名方式，固定格式
     */
    private String signType="RSA2";

    /**
     * 支付宝开放安全地址，一般不会变
     */
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    /**
     * 编码，固定格式
     */
    private String charset= "utf-8";

    /**
     * 异步通知地址
     */
    @NotBlank
    private String notifyUrl;

    /**
     * 订单完成后返回的页面
     */
    @NotBlank
    private String returnUrl;

    /**
     * 类型，固定格式
     */
    private String format="JSON";

    /**
     * 商户号
     */
    @NotBlank
    private String sysServiceProviderId;

}
