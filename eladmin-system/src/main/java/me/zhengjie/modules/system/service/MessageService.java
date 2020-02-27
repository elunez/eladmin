package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Message;
import me.zhengjie.modules.system.service.dto.MessageCriteria;
import me.zhengjie.modules.system.service.dto.MessageDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateQueryCriteria;

/**
 * @author 黄星星
 * @date 2020-02-27
 */
public interface MessageService {

    /**
     * get
     * @param id
     * @return
     */
    MessageDTO findById(long id);

    MessageDTO create(Message resources);

    void delete(Long id);

    Object queryAll(MessageCriteria criteria);

    void update(Message resources);

}
