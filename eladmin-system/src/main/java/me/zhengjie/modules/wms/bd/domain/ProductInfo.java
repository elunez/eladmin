package me.zhengjie.modules.wms.bd.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author 黄星星
* @date 2019-07-27
*/
@Entity
@Data
@Table(name="bd_product_info")
public class ProductInfo implements Serializable {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    // 所属产品分类
    @Column(name = "product_category_id",nullable = false)
    private long productCategoryId;

    // 产品分类名称
    @Column(name = "product_category_name",nullable = false)
    private String productCategoryName;

    // 产品编号
    @Column(name = "product_code")
    private String productCode;

    // 产品名称
    @Column(name = "name")
    private String name;

    // 产品规格
    @Column(name = "specifications")
    private String specifications;

    // 所属计量单位主键
    @Column(name = "measure_unit_id")
    private long measureUnitId;

    // 所属计量单位名称
    @Column(name = "measure_unit_name")
    private String measureUnitName;

    // 产品单价(保留两位小数) 单位:元 
    @Column(name = "unit_price")
    private Long unitPrice;

    // 产品库存预警[{“sort”:1,”wareHouseCode”:””,”wareHouseName”:””,”minimumInventory”:””,”highestInventory”:””}]
    @Column(name = "product_inventory_warning")
    private String productInventoryWarning;

    // 产品期初设置
    //[{“sort”:””,”ware_house_code”:””,”ware_house_name”:””,”ware_house_type_code”:””,“ ware_house_type_name”:””,
	//“material_code”:””,“material_name”:””,“specifications”:””,“unit_price”:””,“total_price”:””,
	//”minimum_inventory”:””,”highest_inventory”:””}]
    @Column(name = "product_initial_setup")
    private String productInitialSetup;

    // 创建时间
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    // 更新时间
    @Column(name = "update_time")
    @CreationTimestamp
    private Timestamp updateTime;

//    @NotNull
    private Boolean status;

    public void copy(ProductInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}