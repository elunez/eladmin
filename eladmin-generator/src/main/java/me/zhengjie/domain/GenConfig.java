package me.zhengjie.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * 代码生成配置
 * @author Zheng Jie
 * @date 2019-01-03
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "gen_config")
public class GenConfig {

    public GenConfig(String tableName) {
        this.cover = false;
        this.moduleName = "eladmin-system";
        this.tableName = tableName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    private String tableName;

    /** 接口名称 **/
    private String apiAlias;

    /** 包路径 */
    @NotBlank
    private String pack;

    /** 模块名 */
    @Column(name = "module_name")
    @NotBlank
    private String moduleName;

    /** 前端文件路径 */
    @NotBlank
    private String path;

    /** 前端文件路径 */
    @Column(name = "api_path")
    private String apiPath;

    /** 作者 */
    private String author;

    /** 表前缀 */
    private String prefix;

    /** 是否覆盖 */
    private Boolean cover;
}
