package me.zhengjie.modules.wms.outSourceProductSheet.request;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
* @author jie
* @date 2019-10-01
*/
@Data
public class CreateOutSourceInspectionCertificateRequest implements Serializable {


    // 所属委外加工单code
    private String outSourceProcessSheetCode;

    // 制单人
    private Long makePeopleId;

    // 制单人姓名
    private String makePeopleName;

    // 委外加工验收单单据编号
    private String outSourceInspectionCertificateCode;

    private String remark;

    private List<OutSourceInspectionCertificateProductRequest> outSourceInspectionCertificateProductList;

    public void copy(CreateOutSourceInspectionCertificateRequest source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}