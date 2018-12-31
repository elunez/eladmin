package me.zhengjie.tools.repository;

import me.zhengjie.tools.domain.QiniuConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jie
 * @date 2018-12-31
 */
public interface QiNiuConfigRepository extends JpaRepository<QiniuConfig,Long> {
}
