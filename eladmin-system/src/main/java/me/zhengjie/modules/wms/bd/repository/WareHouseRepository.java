package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 黄星星
 * @date 2019-07-26
 */
public interface WareHouseRepository extends JpaRepository<WareHouse, Long >, JpaSpecificationExecutor {

    /**
     * 查询指定名字或者仓库编码且状态为存在的仓库
     * @param name
     * @param wareHouseCode
     * @return
     */
    @Query(value ="select * from bd_ware_house where name = ?1 and ware_house_code = ?2 and status = 1", nativeQuery = true)
    List<WareHouse> findByNameOrWareHouseCodeAndStatusTrue(String name, String wareHouseCode);


    /**
     * 查询指定名字和仓库编码且已经删除了的仓库
     * @param name
     * @param wareHouseCode
     * @return
     */
    @Query(value ="select * from bd_ware_house where name = ?1 and ware_house_code = ?2 and status = 0", nativeQuery = true)
    WareHouse findByNameAndWareHouseCodeAndStatusFalse(String name, String wareHouseCode);

    /**
     * 删除仓库(逻辑删除)
     * @param id
     */
    @Modifying
    @Query(value = "update bd_ware_house set status = 0 where id = ?1",nativeQuery = true)
    void deleteWareHouse(long id);

    @Modifying
    @Query(value = "update bd_ware_house set status = 1 where id = ?1",nativeQuery = true)
    void updateStatusTrue(long id);
}
