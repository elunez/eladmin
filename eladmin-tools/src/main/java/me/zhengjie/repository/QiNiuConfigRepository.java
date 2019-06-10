package me.zhengjie.repository;

import me.zhengjie.domain.QiniuConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Zheng Jie
 * @date 2018-12-31
 */
public interface QiNiuConfigRepository extends JpaRepository<QiniuConfig,Long> {
}
