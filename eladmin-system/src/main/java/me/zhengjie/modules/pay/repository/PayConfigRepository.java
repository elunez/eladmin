package me.zhengjie.modules.pay.repository;

import me.zhengjie.modules.pay.domain.PayConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author xpay
 * @date 2019-06-02
 */
public interface PayConfigRepository extends JpaRepository<PayConfig, Long>, JpaSpecificationExecutor {
    /**
     * findByUid
     *
     * @param uid
     * @return
     */
    PayConfig findByUid(Long uid);
}