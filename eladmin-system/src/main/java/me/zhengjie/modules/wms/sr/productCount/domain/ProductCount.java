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

    @Column(name = "total_number")
    private Long totalNumber;

    @Column(name = "gmt_create")
    @CreationTimestamp
    private Timestamp gmtCreate;

    @Column(name = "gmt_update")
    @CreationTimestamp
    private Timestamp gmtUpdate;

    public void copy(ProductCount source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}