package me.zhengjie.modules.quartz.repository;

import me.zhengjie.modules.quartz.domain.QuartzJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author jie
 * @date 2019-01-07
 */
public interface QuartzJobRepository extends JpaRepository<QuartzJob,Long>, JpaSpecificationExecutor {

    /**
     * 更新状态
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update quartz_job set is_pause = 1 where id = ?1",nativeQuery = true)
    void updateIsPause(Long id);

    /**
     * 查询不是启用的任务
     * @return
     */
    List<QuartzJob> findByIsPauseIsFalse();
}
