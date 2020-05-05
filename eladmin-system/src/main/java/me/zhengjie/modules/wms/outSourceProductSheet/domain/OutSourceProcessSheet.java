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
* @date 2019-08-17
*/
@Entity
@Data
@Table(name="s_out_source_process_sheet")
public class
OutSourceProcessSheet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 状态
    @Column(name = "status")
    private Boolean status;

    // 创建时间
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    // 更新时间
    @Column(name = "update_time")
    @CreationTimestamp
    private Timestamp updateTime;

    // 委外加工公司名称
    @Column(name = "out_source_company_name")
    private String outSourceCompanyName;

    // 委外加工公司编号
    @Column(name = "out_source_company_code")
    private String outSourceCompanyCode;

    // 委外负责人id
    @Column(name = "out_source_admin_id")
    private Integer outSourceAdminId;

    // 委外负责人姓名
    @Column(name = "out_source_admin_name")
    private String outSourceAdminName;

    // 联系方式
    @Column(name = "contact_way")
    private String contactWay;

    // 委外加工单单据编号
    @Column(name = "out_source_process_sheet_code")
    private String outSourceProcessSheetCode;

    // 委外加工单状态
    @Column(name = "proc_status")
    private String procStatus;

    public void copy(OutSourceProcessSheet source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}