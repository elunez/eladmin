package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.NwIncidents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author Bobby Kimutai
* @date 2019-08-05
*/
public interface NwIncidentsRepository extends JpaRepository<NwIncidents, Integer>, JpaSpecificationExecutor {
}