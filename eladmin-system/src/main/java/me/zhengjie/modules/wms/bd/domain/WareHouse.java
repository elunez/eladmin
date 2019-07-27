package me.zhengjie.modules.wms.bd.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 仓库
 * @author 黄星星
 * @date 2019-07-26
 */
@Entity
@Data
@Table(name="bd_ware_house")
public class WareHouse implements Serializable {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = WareHouse.Update.class)
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name",nullable = false)
    @NotBlank
    private String name;


    /**
     * 仓库编码
     */
    @Column(name = "ware_house_code",nullable = false)
    @NotBlank
    private String wareHouseCode;



    @NotNull
    private Boolean status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @CreationTimestamp
    private Timestamp updateTime;

    public @interface Update {}
}
