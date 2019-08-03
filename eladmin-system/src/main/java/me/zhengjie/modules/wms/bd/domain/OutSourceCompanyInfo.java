package me.zhengjie.modules.wms.bd.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author jie
* @date 2019-08-03
*/
@Entity
@Data
@Table(name="bd_out_source_company_info")
public class OutSourceCompanyInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 委外公司名称
    @Column(name = "out_source_company_name",nullable = false)
    private String outSourceCompanyName;

    // 期初预收款
    @Column(name = "initial_pre_money")
    private Long initialPreMoney;

    // 委外公司编号
    @Column(name = "out_source_company_code",nullable = false)
    private String outSourceCompanyCode;

    // 创建时间
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    // 更新时间
    @Column(name = "update_time")
    @CreationTimestamp
    private Timestamp updateTime;

    // 备注
    @Column(name = "remark")
    private String remark;

    // 委外公司地址数组[{“province”:””,”city”:””,”area”:””,”address_detail”:””,”sort”:””}]
    @Column(name = "out_source_company_address")
    private String outSourceCompanyAddress;

    // 委外公司联系人[{“sort”:””,”name”:””,”mobile”:””,”phone”:””,”email”:””,”qq”:””,”weixin”:””,”firstTag”:””}]firstTag 0:非首要联系人 1:首要联系人
    @Column(name = "out_source_company_contact")
    private String outSourceCompanyContact;

    @Column(name = "status")
    private Boolean status;

    public void copy(OutSourceCompanyInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}