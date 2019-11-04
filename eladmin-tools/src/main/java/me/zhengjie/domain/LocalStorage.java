package me.zhengjie.domain;

import lombok.*;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.base.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Getter
@Setter
@Entity
@Table(name="local_storage")
@NoArgsConstructor
public class LocalStorage  implements Serializable {

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

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

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