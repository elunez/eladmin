package me.zhengjie.modules.system.cons;

/**
 * @author 黄星星
 * @date 2020-02-27
 */
public enum MessageModuleType {
    CUSTOMER_ORDER("客户订单", "CUSTOMER_ORDER"),
    INVOICE("销售发货单", "INVOICE"),
    OUT_SOURCE_PROCESS("委外加工单", "OUT_SOURCE_PROCESS"),
    OUT_SOURCE_INSPECTION_CERTIFICATE("委外验收单", "OUT_SOURCE_INSPECTION_CERTIFICATE"),
    PRODUCT_PURCHASE("产品采购单", "PRODUCT_PURCHASE"),
    CONSUMABLES_PURCHASE("耗材采购单", "CONSUMABLES_PURCHASE"),
    QUALITY_CHECK_SHEET("质量检验单", "QUALITY_CHECK_SHEET"),

    ;
    private String name;
    private String code;

    MessageModuleType(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
