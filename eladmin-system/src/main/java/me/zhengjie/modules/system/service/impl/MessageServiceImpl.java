package me.zhengjie.modules.system.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.Menu;
import me.zhengjie.modules.system.domain.Message;
import me.zhengjie.modules.system.repository.MessageRepository;
import me.zhengjie.modules.system.service.MessageService;
import me.zhengjie.modules.system.service.dto.MessageCriteria;
import me.zhengjie.modules.system.service.dto.MessageDTO;
import me.zhengjie.modules.system.service.mapper.MessageMapper;
import me.zhengjie.modules.wms.invoice.domain.Invoice;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateQueryCriteria;
import me.zhengjie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 黄星星
 * @date 2020-02-27
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public MessageDTO findById(long id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        Message message = messageOptional.get();
        message.setStatus(false);
        messageRepository.save(message);

        MessageDTO messageDTO = messageMapper.toDto(message);
        messageDTO.setCompleteStatus(1);
        return messageDTO;
    }

    @Override
    public MessageDTO create(Message resources) {

        String initCode = resources.getInitCode();
        if(StringUtils.isEmpty(initCode)){
            throw new BadRequestException("单据编号不能为空");
        }

        String modulePath = resources.getModulePath();
        if(StringUtils.isEmpty(modulePath)){
            throw new BadRequestException("模块路径不能为空");
        }

        String moduleTypeName = resources.getModuleTypeName();
        if(StringUtils.isEmpty(moduleTypeName)){
            throw new BadRequestException("模块类型名称不能为空!");
        }

        String moduleTypeCode = resources.getModuleTypeCode();
        if(StringUtils.isEmpty(moduleTypeCode)){
            throw new BadRequestException("模块类型编码不能为空!");
        }
        Message message = messageRepository.save(resources);
        return messageMapper.toDto(message);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        messageRepository.deleteMessageById(id);;
    }

    @Override
    public Object queryAll(MessageCriteria criteria) {
        Specification<Message> specification = new Specification<Message>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")),criteriaBuilder.desc(root.get("createTime")));
                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };

        List<Message> messageList = messageRepository.findAll(specification);
        List<MessageDTO> messageDtoList = messageMapper.toDto(messageList);
        return messageDtoList;
    }

    @Override
    public MessageDTO update(Message resources) {
        Optional<Message> messageOptional = messageRepository.findById(resources.getId());
        Message message = messageOptional.get();
        message.setStatus(false);
        messageRepository.save(resources);

        MessageDTO messageDTO = messageMapper.toDto(message);
        messageDTO.setCompleteStatus(1);
        return messageDTO;
    }
}
