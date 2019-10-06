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
    private Long outSourceInspectionCertificateId;

    private String productCode;

    private Long productId;

    private String productName;

    private Integer productNumber;

    private String remark;

    // 合格数量
    private Integer qualifiedNumber;

    // 报废数量
    private Integer scrapNumber;

    public void copy(OutSourceInspectionCertificateProductRequest source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}