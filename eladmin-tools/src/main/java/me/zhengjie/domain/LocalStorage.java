package me.zhengjie.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Entity
@Data
@Table(name="local_storage")
@AllArgsConstructor
@NoArgsConstructor
public class LocalStorage implements Serializable {

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 真实文件名
    @Column(name = "real_name")
    private String realName;

    // 文件名
    @Column(name = "name")
    private String name;

    // 后缀
    @Column(name = "suffix")
    private String suffix;

    // 路径
    @Column(name = "path")
    private String path;

    // 类型
    @Column(name = "type")
    private String type;

    // 大小
    @Column(name = "size")
    private String size;

    // 操作人
    @Column(name = "operate")
    private String operate;

    // 创建日期
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    // 修改日期
    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    public LocalStorage(String realName,String name, String suffix, String path, String type, String size, String operate) {
        this.realName = realName;
        this.name = name;
        this.suffix = suffix;
        this.path = path;
        this.type = type;
        this.size = size;
        this.operate = operate;
    }

    public void copy(LocalStorage source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}