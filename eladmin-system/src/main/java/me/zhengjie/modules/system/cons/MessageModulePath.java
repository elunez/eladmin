package me.zhengjie.modules.system.cons;

/**
 * @author 黄星星
 * @date 2020-02-27
 */
public enum MessageModulePath {
    CUSTOMER_ORDER_LIST("客户订单列表","order/customerOrder"),

    ;

    private String name;
    private String code;

    MessageModulePath(String name, String code) {
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
