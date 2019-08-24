package me.zhengjie.modules.wms.bd.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄星星
 * [{“sort”:””,”name”:””,”mobile”:””,”phone”:””,”email”:””,”qq”:””,”weixin”:””,”firstTag”:””}]firstTag 0:非首要联系人 1:首要联系人
 * @date 2019-08-18
 */
@Data
public class OutSourceCompanyContact implements Serializable {

    private Integer sort;

    private String name;

    private String mobile;

    private String phone;

    private String email;

    private String qq;

    private String weixin;

    private Integer firstTag;

}
