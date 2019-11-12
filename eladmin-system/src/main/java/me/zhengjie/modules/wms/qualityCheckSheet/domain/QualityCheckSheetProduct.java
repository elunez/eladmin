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
@Table(name="quality_check_sheet_product")
public class QualityCheckSheetProduct implements Serializable {

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

    // 所属质量检验单
    @Column(name = "quality_check_sheet_id")
    private Long qualityCheckSheetId;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "remark")
    private String remark;

    // 产品个数
    @Column(name = "product_number")
    private Integer productNumber;

    // 合格个数
    @Column(name = "qualified_number")
    private Integer qualifiedNumber;

    // 报废数量
    @Column(name = "scrap_number")
    private Integer scrapNumber;

    public void copy(QualityCheckSheetProduct source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}