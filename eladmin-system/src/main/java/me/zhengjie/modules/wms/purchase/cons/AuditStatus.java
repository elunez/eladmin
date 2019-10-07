package me.zhengjie.modules.wms.purchase.cons;

/**
 * @author 黄星星
 * @date 2019-10-07
 */
public enum AuditStatus {
    AUDIT_STATUS_PASS("pass", "同意"),
    AUDIT_STATUS_REJECT("reject", "拒绝"),
    AUDIT_STATUS_NO_AUDIT("no_audit", "未审核"),
    ;
    private String code;
    private String name;

    AuditStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getName(String code) {
        AuditStatus[] auditStatuseEnums = values();
        for (AuditStatus auditStatus : auditStatuseEnums) {
            if (auditStatus.getCode().equals(code)) {
                return auditStatus.getName();
            }
        }
        return null;
    }
}