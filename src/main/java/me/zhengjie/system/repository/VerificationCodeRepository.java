package me.zhengjie.system.repository;

import me.zhengjie.system.domain.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jie
 * @date 2018-12-26
 */
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

}
