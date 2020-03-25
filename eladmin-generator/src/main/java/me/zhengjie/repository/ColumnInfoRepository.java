package me.zhengjie.repository;

import me.zhengjie.domain.ColumnInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2019-01-14
 */
public interface ColumnInfoRepository extends JpaRepository<ColumnInfo,Long> {

    /**
     * 查询表信息
     * @param tableName 表格名
     * @return 表信息
     */
    List<ColumnInfo> findByTableNameOrderByIdAsc(String tableName);
}
