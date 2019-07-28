package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.MaterialCategory;
import me.zhengjie.modules.wms.bd.domain.MaterialInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
* @author 黄星星
* @date 2019-07-27
*/
public interface MaterialInfoRepository extends JpaRepository<MaterialInfo, Long>, JpaSpecificationExecutor {

    /**
     * 根据物料编码查询状态存在的物料资料
     * @param materialCode
     * @return
     */
    MaterialInfo findByMaterialCodeAndStatusTrue(String materialCode);

    /**
     * 根据主键查询状态正常的物料资料
     * @param id
     * @return
     */
    MaterialInfo findByIdAndStatusTrue(long id);

    /**
     * 删除物料资料
     * @param id
     */
    @Modifying
    @Query(value = "update bd_material_info set status = 0 where id = ?1",nativeQuery = true)
    void deleteMaterialInfo(long id);
}