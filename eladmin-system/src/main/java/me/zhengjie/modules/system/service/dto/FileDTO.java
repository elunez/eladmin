package me.zhengjie.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Data
public class FileDTO implements Serializable {

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String fileName;


    private String fileType;

    private String originName;

    /**
     * 上级部门
     */
    private String uploader;

    private Timestamp createTime;

    private FileSortDTO fileSort;

    private String fileSortId;
}