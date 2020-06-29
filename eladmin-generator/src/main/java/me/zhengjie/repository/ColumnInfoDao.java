package me.zhengjie.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import me.zhengjie.base.BaseDao;
import me.zhengjie.domain.ColumnInfo;
import me.zhengjie.repository.jpa.ColumnInfoRepository;
import me.zhengjie.repository.mp.ColumnInfoService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liaojinlong
 * @since 2020/6/28 14:59
 */
@Component
public class ColumnInfoDao extends BaseDao<ColumnInfoService, ColumnInfoRepository, ColumnInfo, Long> {

    public ColumnInfoDao(ColumnInfoService baseService, ColumnInfoRepository jpaRepository ) {
        super(baseService, jpaRepository);
    }

    public List<ColumnInfo> findByTableNameOrderByIdAsc(String tableName) {
        if (dbSwitch) {
            return jpaRepository.findByTableNameOrderByIdAsc(tableName);
        } else {
            return mpService
                    .list(Wrappers.<ColumnInfo>query().eq(true, "TABLE_NAME", tableName));
        }
    }

}
