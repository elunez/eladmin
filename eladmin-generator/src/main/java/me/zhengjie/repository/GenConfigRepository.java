package me.zhengjie.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import me.zhengjie.base.BaseRepository;
import me.zhengjie.domain.GenConfig;
import me.zhengjie.repository.jpa.GenConfigJpaRepository;
import me.zhengjie.repository.mp.GenConfigMpService;
import org.springframework.stereotype.Repository;


/**
 * JPA 与Mybatis 适配DAO
 *
 * @author liaojinlong
 * @since 2020/7/1 23:00
 */
@Repository
public class GenConfigRepository extends BaseRepository<GenConfigMpService, GenConfigJpaRepository, GenConfig, Long> {
    public GenConfigRepository(GenConfigMpService mpService, GenConfigJpaRepository jpaRepository) {
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
