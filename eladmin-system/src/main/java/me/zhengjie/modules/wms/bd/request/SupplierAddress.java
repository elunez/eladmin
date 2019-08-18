package me.zhengjie.modules.wms.bd.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄星星
 * @date 2019-08-18
 */
@Data
public class SupplierAddress implements Serializable {
    private String province;

    private String city;

    private String area;

    private String addressDetail;

    private Integer sort;
}
