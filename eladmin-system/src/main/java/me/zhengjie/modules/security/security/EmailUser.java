package me.zhengjie.modules.security.security;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author jie
 * @date 2018-11-30
 */
@Getter
@Setter
public class EmailUser {

    @NotBlank
    private String email;

    
}
