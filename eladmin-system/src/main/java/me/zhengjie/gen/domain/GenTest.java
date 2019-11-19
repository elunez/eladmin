package me.zhengjie.gen.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author Zheng Jie
* @date 2019-11-19
*/
@Entity
@Data
@Table(name="gen_test")
public class GenTest implements Serializable {

    // ID
    @Id
    @Column(name = "id")
    private Long id;

    // 名称
    @Column(name = "name",nullable = false)
    @NotBlank
    private String name;

    // 状态
    @Column(name = "status",nullable = false)
    @NotNull
    private Boolean status;

    // 日期
    @Column(name = "date",nullable = false)
    @NotNull
    private Timestamp date;

    // 创建日期
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    public void copy(GenTest source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}