package me.zhengjie.modules.wms.outSourceProductSheet.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
 * 委外验收单实体类
* @author jie
* @date 2019-10-01
*/
@Entity
@Data
@Table(name="s_out_source_inspection_certificate")
public class OutSourceInspectionCertificate implements Serializable {

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

    // 所属委外加工单
    @Column(name = "out_source_process_sheet_code")
    private String outSourceProcessSheetCode;

    // 制单人
    @Column(name = "make_people_id")
    private Long makePeopleId;

    // 制单人姓名
    @Column(name = "make_people_name")
    private String makePeopleName;

    // 委外加工验收单单据编号
    @Column(name = "out_source_inspection_certificate_code")
    private String outSourceInspectionCertificateCode;

    @Column(name = "remark")
    private String remark;

    public void copy(OutSourceInspectionCertificate source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}