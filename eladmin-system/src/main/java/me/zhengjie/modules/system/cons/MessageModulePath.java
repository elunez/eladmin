package me.zhengjie.modules.system.cons;

/**
 * @author 黄星星
 * @date 2020-02-27
 */
public enum MessageModulePath {
    CUSTOMER_ORDER_LIST("客户订单列表","order/customerOrder"),
    DELIVERY_ORDER_INFO_LIST("销售发货单列表","order/deliveryOrderInfo"),
    OUT_SOURCE_LIST("委外加工单列表","outSource/outSourceProcess"),
    OUT_SOURCE_INSPECTION_CERTIFICATE_LIST("委外验收单列表","outSource/outSourceInspectionCertificate"),
    PRODUCT_PURCHASE_LIST("产品采购单列表","purchase/productPurchase"),
    CONSUMABLES_PURCHASE_LIST("耗材采购单列表","purchase/consumablesPurchase"),
    QUALITY_CHECKSHEET_LIST("质量检验单列表","productQuality/qualityCheckSheet"),

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
