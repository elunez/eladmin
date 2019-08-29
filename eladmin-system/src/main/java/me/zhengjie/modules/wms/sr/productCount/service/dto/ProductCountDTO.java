package me.zhengjie.modules.wms.sr.productCount.service.dto;

import lombok.Data;
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

    private Long totalNumber;

    private Timestamp gmtCreate;

    private Timestamp gmtUpdate;
}