package me.zhengjie.modules.pay.domain;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
* @author xpay
* @date 2019-06-02
*/
@Entity
@Data
@Table(name="pay_config")
public class PayConfig implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 支付宝图片
     */
    @Column(name = "img_alipay")
    private String imgAlipay;

    /**
     * 微信图片
     */
    @Column(name = "img_wechat")
    private String imgWechat;

    /**
     * QQ支付图片
     */
    @Column(name = "img_qq")
    private String imgQq;

    /**
     * 银联支付图片
     */
    @Column(name = "img_unionpay")
    private String imgUnionpay;

    /**
     * 用户ID
     */
    @Column(name = "uid",nullable = false)
    private Long uid;

    /**
     * 异步回调
     */
    @Column(name = "notify_url")
    private String notifyUrl;

    /**
     * 私钥
     */
    @Column(name = "private_key")
    private String privateKey;

    /**
     * 公钥
     */
    @Column(name = "public_key")
    private String publicKey;

    /**
     * 商户号
     */
    @Column(name = "sys_service_provider_id")
    private String sysServiceProviderId;
}