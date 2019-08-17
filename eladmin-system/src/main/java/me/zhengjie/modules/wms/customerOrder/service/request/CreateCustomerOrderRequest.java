package me.zhengjie.modules.wms.customerOrder.service.request;

import lombok.Data;
import me.zhengjie.modules.wms.customerOrder.domain.CustomerOrderProduct;
import me.zhengjie.modules.wms.customerOrder.domain.CustomerOrder;

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
