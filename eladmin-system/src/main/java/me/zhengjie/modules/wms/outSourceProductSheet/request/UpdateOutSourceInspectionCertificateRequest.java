package me.zhengjie.modules.wms.outSourceProductSheet.request;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateProductDTO;

import java.io.Serializable;
import java.util.List;

/**
* @author jie
* @date 2019-10-01
*/
@Data
public class UpdateOutSourceInspectionCertificateRequest implements Serializable {

    private Long id;


    // 所属委外加工单
    private Long outSourceProcessSheetId;

    // 制单人
    private Long makePeopleId;

    // 制单人姓名
    private String makePeopleName;

    // 委外加工验收单单据编号
    private String outSourceInspectionCertificateCode;

    private String remark;

    // 委外验收单产品信息
    private List<OutSourceInspectionCertificateProductDTO> outSourceInspectionCertificateProductList;

    public void copy(UpdateOutSourceInspectionCertificateRequest source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}