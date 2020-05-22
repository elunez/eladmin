package me.zhengjie.modules.wms.sr.productCount.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author jie
* @date 2019-08-29
*/
@Entity
@Data
@Table(name="sr_product_count")
public class ProductCount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "total_number")
    private Long totalNumber;

    @Column(name = "gmt_create")
    @CreationTimestamp
    private Timestamp gmtCreate;

    @Column(name = "gmt_update")
    @CreationTimestamp
    private Timestamp gmtUpdate;

    @Column(name = "product_category_id")
    private Long productCategoryId;

    @Column(name = "product_category_name")
    private String productCategoryName;

    @Column(name = "product_series_id")
    private Long productSeriesId;

    @Column(name = "product_series_name")
    private String productSeriesName;

    @Column(name = "mp_number")
    private Long mpNumber;

    @Column(name = "hj_number")
    private Long hjNumber;

    @Column(name = "bjc_number")
    private Long bjcNumber;

    @Column(name = "jc_number")
    private Long jcNumber;

    @Column(name = "jm_number")
    private Long jmNumber;

    @Column(name = "dph_number")
    private Long dphNumber;

    @Column(name = "cm_number")
    private Long cmNumber;

    public void copy(ProductCount source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}