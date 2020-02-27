package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 黄星星
 * @date 2020-02-27
 */
public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor {

}
