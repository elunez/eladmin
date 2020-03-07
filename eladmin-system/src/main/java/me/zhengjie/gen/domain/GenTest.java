package me.zhengjie.gen.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author Zheng Jie
* @date 2020-03-07
*/
@Entity
@Data
@Table(name="gen_test")
public class GenTest implements Serializable {

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /** 名称 */
    @Column(name = "name",nullable = false)
    @NotBlank
    private String name;

    /** 性别 */
    @Column(name = "sex")
    private Integer sex;

    @Column(name = "create_time")
    private Timestamp createTime;

    public void copy(GenTest source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}