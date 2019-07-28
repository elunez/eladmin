package me.zhengjie.modules.wms.bd.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author 黄星星
* @date 2019-07-27
*/
@Entity
@Data
@Table(name="bd_material_info")
public class MaterialInfo implements Serializable {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    // 所属物料分类主键
    @Column(name = "material_category_id",nullable = false)
    private long materialCategoryId;

    // 物料分类名称
    @Column(name = "material_category_name",nullable = false)
    private String materialCategoryName;

    // 物料名称
    @Column(name = "name")
    private String name;

    // 物料编码
    @Column(name = "material_code")
    private String materialCode;

    // 物料规格
    @Column(name = "specifications")
    private String specifications;

    // 所属计量单位主键
    @Column(name = "measure_unit_id")
    private Long measureUnitId;

    // 所属计量单位名称
    @Column(name = "measure_unit_name")
    private String measureUnitName;

    // 产品库存预警[{“sort”:””,”ware_house_code”:””,”ware_house_name”:””,”minimum_inventory”:””,”highest_inventory”:””}]
    @Column(name = "material_inventory_warning")
    private String materialInventoryWarning;

    // 产品期初设置[{“sort”:””,”ware_house_code”:””,”ware_house_name”:””,“initial_setup_numer”:””,“initial_setup_total_price”:””}]
    @Column(name = "material_initial_setup")
    private String materialInitialSetup;

    // 物料期初合计期初总价格

    @Column(name = "material_initial_setup_total_price")
    private Integer materialInitialSetupTotalPrice;

    // 物料期初合计总数量
    @Column(name = "material_initial_setup_total_number")
    private String materialInitialSetupTotalNumber;

    // 创建时间
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    // 更新时间
    @Column(name = "update_time")
    @CreationTimestamp
    private Timestamp updateTime;

    public void copy(MaterialInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}