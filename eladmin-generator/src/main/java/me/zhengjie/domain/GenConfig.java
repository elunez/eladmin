package me.zhengjie.domain;

import lombok.Data;
import javax.persistence.*;

/**
 * 代码生成配置
 * @author Zheng Jie
 * @date 2019-01-03
 */
@Data
@Entity
@Table(name = "gen_config")
public class GenConfig {

    @Id
    private Long id;

    /** 包路径 **/
    private String pack;

    /** 模块名 **/
    @Column(name = "module_name")
    private String moduleName;

    /** 前端文件路径 **/
    private String path;

    /** 前端文件路径 **/
    @Column(name = "api_path")
    private String apiPath;

    /** 作者 **/
    private String author;

    /** 表前缀 **/
    private String prefix;

    /** 是否覆盖 **/
    private Boolean cover;
}
