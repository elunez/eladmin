package me.zhengjie.modules.wms.sr.productCount.service.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author jie
* @date 2019-08-29
*/
@Data
public class ProductCountDTO implements Serializable {

    private Long id;

    private Long productId;

    private String productName;

    private String productCode;

    private Long totalNumber;

    private Timestamp gmtCreate;

    private Timestamp gmtUpdate;

    private Long productCategoryId;

    private String productCategoryName;

    private Long productSeriesId;

    private String productSeriesName;

    private Long mpNumber;

    private Long hjNumber;

    private Long bjcNumber;

    private Long jcNumber;

    private Long jmNumber;

    private Long dphNumber;

    private Long cmNumber;
}