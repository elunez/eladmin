package me.zhengjie.modules.wms.qualityCheckSheet.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huangxingxing
* @date 2019-11-12
*/
@Entity
@Data
@Table(name="quality_check_sheet")
public class QualityCheckSheet implements Serializable {

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

    // 制单人
    @Column(name = "make_people_id")
    private Long makePeopleId;

    // 制单人姓名
    @Column(name = "make_people_name")
    private String makePeopleName;

    // 质量检验单单据编号
    @Column(name = "quality_cheek_sheet_code")
    private String qualityCheekSheetCode;

    @Column(name = "remark")
    private String remark;

    public void copy(QualityCheckSheet source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}