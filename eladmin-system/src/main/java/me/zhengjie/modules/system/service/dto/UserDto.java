package me.zhengjie.modules.system.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Data
public class UserDto implements Serializable {

    @ApiModelProperty(hidden = true)
    private Long id;

    private String username;

    private String nickName;

    private String sex;

    private String avatar;

    private String email;

    private String phone;

    private Boolean enabled;

    @JsonIgnore
    private String password;

    private Date lastPasswordResetTime;

    @ApiModelProperty(hidden = true)
    private Set<RoleSmallDto> roles;

    @ApiModelProperty(hidden = true)
    private JobSmallDto job;

    private DeptSmallDto dept;

    private Long deptId;

    private Timestamp createTime;
}
