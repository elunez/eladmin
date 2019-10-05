package me.zhengjie.modules.wms.bd.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author jie
* @date 2019-10-05
*/
@Entity
@Data
@Table(name="bd_consumables_info")
public class ConsumablesInfo implements Serializable {

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

    // 耗材名称
    @Column(name = "consumables_name")
    private String consumablesName;

    @Column(name = "status")
    private Boolean status;

    public void copy(ConsumablesInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}