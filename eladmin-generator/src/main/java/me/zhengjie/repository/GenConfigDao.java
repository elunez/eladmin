package me.zhengjie.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import me.zhengjie.base.mybatis.BaseDao;
import me.zhengjie.domain.GenConfig;
import me.zhengjie.repository.jpa.GenConfigRepository;
import me.zhengjie.repository.mp.GenConfigService;
import org.springframework.stereotype.Repository;


/**
 * JPA 与Mybatis 适配DAO
 *
 * @author liaojinlong
 * @since 2020/7/1 23:00
 */
@Repository
public class GenConfigDao extends BaseDao<GenConfigService, GenConfigRepository, GenConfig, Long> {
    public GenConfigDao(GenConfigService mpService, GenConfigRepository jpaRepository) {
        super(mpService, jpaRepository);
    }

    public GenConfig findByTableName(String tableName) {
        GenConfig result;
        switch (dbType) {
            case JPA:
                result = jpaRepository.findByTableName(tableName);
                break;
            case MYBATIS:
                result = mpService
                        .getOne(Wrappers.<GenConfig>query().eq(true, "TABLE_NAME", tableName));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbType);
        }
        return result;
    }
}
