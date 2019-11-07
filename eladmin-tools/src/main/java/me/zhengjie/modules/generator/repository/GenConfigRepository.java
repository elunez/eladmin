package me.zhengjie.modules.generator.repository;

import me.zhengjie.modules.generator.domain.GenConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Zheng Jie
 * @date 2019-01-14
 */
public interface GenConfigRepository extends JpaRepository<GenConfig,Long> {
}
