package me.zhengjie.modules.system.cons;

/**
 * @author 黄星星
 * @date 2020-02-27
 */
public enum MessageModuleType {
    CUSTOMER_ORDER("客户订单", "CUSTOMER_ORDER"),
    INVOICE("销售发货单", "INVOICE")

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
