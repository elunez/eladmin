package me.zhengjie.constant;

/**
 * <p>
 * 安全定义
 * </p>
 *
 * @author miaoyj
 * @version 1.0.0-SNAPSHOT
 * @since 2020-08-12
 */
public interface SecurityConstant {

    /**
     * 用户id
     */
    String JWT_KEY_USER_ID = "userid";

    /**
     * 用户名称
     */
    String JWT_KEY_USERNAME = "username";

    /**
     * 用户权限
     */
    String JWT_KEY_PERMISSION = "permissions";
    /**
     * 用户数据范围-部门ids
     */
    String JWT_KEY_DATA_SCOPE_DEPT_IDS = "data_scope_dept_ids";
}