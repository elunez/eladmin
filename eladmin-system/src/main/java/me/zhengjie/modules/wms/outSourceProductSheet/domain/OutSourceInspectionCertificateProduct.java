package me.zhengjie.modules.wms.outSourceProductSheet.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author jie
* @date 2019-10-01
*/
@Entity
@Data
@Table(name="s_out_source_inspection_certificate_product")
public class OutSourceInspectionCertificateProduct implements Serializable {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "update_time")
    @CreationTimestamp
    private Timestamp updateTime;

    @Column(name = "status")
    private Boolean status;

    // 所属委外验收单
    @Column(name = "out_source_inspection_certificate_id")
    private Long outSourceInspectionCertificateId;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "remark")
    private String remark;

    public void copy(OutSourceInspectionCertificateProduct source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}