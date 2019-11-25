package me.zhengjie.modules.mnt.service;

import me.zhengjie.modules.mnt.domain.DeployHistory;
import me.zhengjie.modules.mnt.service.dto.DeployHistoryDTO;
import me.zhengjie.modules.mnt.service.dto.DeployHistoryQueryCriteria;
import org.springframework.data.domain.Pageable;

public interface DeployHistoryService {

    Object queryAll(DeployHistoryQueryCriteria criteria, Pageable pageable);

    Object queryAll(DeployHistoryQueryCriteria criteria);

    DeployHistoryDTO findById(String id);

    DeployHistoryDTO create(DeployHistory resources);

    void delete(String id);
}
