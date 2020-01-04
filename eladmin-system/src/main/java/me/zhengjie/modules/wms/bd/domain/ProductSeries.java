package me.zhengjie.modules.wms.bd.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author huangxingxing
* @date 2020-01-04
*/
@Entity
@Data
@Table(name="bd_product_series")
public class ProductSeries implements Serializable {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 产品系列名称
    @Column(name = "product_series_name")
    private String productSeriesName;

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

    public void copy(ProductSeries source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}