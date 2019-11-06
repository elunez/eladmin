package me.zhengjie.modules.system.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Data
public class FileSortDTO implements Serializable {

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    @NotNull
    private Boolean enabled;

    /**
     * 上级部门
     */
    private String pid;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<FileSortDTO> children;

    private Timestamp createTime;

    public String getLabel() {
        return name;
    }
}