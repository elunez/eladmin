package me.zhengjie.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
* @author Zheng Jie
* @date 2019-6-10 16:32:18
*/
@Data
public class FileSmallDTO implements Serializable {

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String fileName;
}