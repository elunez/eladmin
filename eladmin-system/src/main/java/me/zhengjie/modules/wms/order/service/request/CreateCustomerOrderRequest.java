package me.zhengjie.modules.wms.order.service.request;

import lombok.Data;
import me.zhengjie.modules.wms.order.domain.CustomerOrder;
import me.zhengjie.modules.wms.order.domain.CustomerOrderProduct;

import java.util.List;

/**
 * @author 黄星星
 * @date 2019-08-03
 */
@Data
public class CreateCustomerOrderRequest {

    private CustomerOrder customerOrder;

    private List<CustomerOrderProduct> customerOrderProductList;
}
