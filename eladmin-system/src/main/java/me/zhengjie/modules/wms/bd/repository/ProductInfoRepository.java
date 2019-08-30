package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
* @author 黄星星
* @date 2019-07-27
*/
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long>, JpaSpecificationExecutor {

    /**
     * 根据转查询状态正常的产品资料
     * @param id
     * @return
     */
    ProductInfo findByIdAndStatusTrue(long id);


    ProductInfo findByProductCode(String productCode);


    /**
     * 删除产品资料
     * @param id
     */
    @Modifying
    @Query(value = "update bd_product_info set status = 0 where id = ?1",nativeQuery = true)
    void deleteProductInfo(long id);
}