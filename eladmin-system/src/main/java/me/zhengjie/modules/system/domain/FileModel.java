package me.zhengjie.modules.system.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
*
*
*/
@Entity
@Data
@Table(name="file")
public class FileModel implements Serializable {

    /**s
     * ID
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private String id;

    /**
     * 名称
     */
    @Column(name = "file_name",nullable = false)
    @NotBlank
    private String fileName;

    /**
     * 名称
     */
    @Column(name = "origin_name",nullable = false)
    @NotBlank
    private String originName;


    /**
     * 所属目录
     */
    @OneToOne
    @JoinColumn(name = "filesort_id")
    private FileSort fileSort;

    /**
     * 文件类型
    */
    @Column(name = "file_type",nullable = false)
    @NotNull
    private String fileType;

    /**
     * 上传人
     */
    @Column(name = "uploader",nullable = false)
    @NotNull
    private String uploader;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    public @interface Update {}
}