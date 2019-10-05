package me.zhengjie.modules.wms.outSourceProductSheet.request;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author jie
* @date 2019-10-01
*/
@Data
public class OutSourceInspectionCertificateProductRequest implements Serializable {

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

    public void copy(OutSourceInspectionCertificateProductRequest source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}