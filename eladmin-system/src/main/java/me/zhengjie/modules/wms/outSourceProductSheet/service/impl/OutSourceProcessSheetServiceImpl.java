package me.zhengjie.modules.wms.outSourceProductSheet.service.impl;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.outSourceProductSheet.repository.OutSourceProcessSheetRepository;
import me.zhengjie.modules.wms.outSourceProductSheet.service.OutSourceProcessSheetService;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetQueryCriteria;
import me.zhengjie.modules.wms.outSourceProductSheet.service.mapper.OutSourceProcessSheetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

/**
* @author jie
* @date 2019-08-17
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OutSourceProcessSheetServiceImpl implements OutSourceProcessSheetService {

    @Autowired
    private OutSourceProcessSheetRepository outSourceProcessSheetRepository;

    @Autowired
    private OutSourceProcessSheetMapper outSourceProcessSheetMapper;

    @Override
    public Object queryAll(OutSourceProcessSheetQueryCriteria criteria, Pageable pageable){
        Page<OutSourceProcessSheet> page = outSourceProcessSheetRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(outSourceProcessSheetMapper::toDto));
    }

    @Override
    public Object queryAll(OutSourceProcessSheetQueryCriteria criteria){
        return outSourceProcessSheetMapper.toDto(outSourceProcessSheetRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public OutSourceProcessSheetDTO findById(Long id) {
        Optional<OutSourceProcessSheet> sOutSourceProcessSheet = outSourceProcessSheetRepository.findById(id);
        ValidationUtil.isNull(sOutSourceProcessSheet,"SOutSourceProcessSheet","id",id);
        return outSourceProcessSheetMapper.toDto(sOutSourceProcessSheet.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutSourceProcessSheetDTO create(OutSourceProcessSheet resources) {
        return outSourceProcessSheetMapper.toDto(outSourceProcessSheetRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OutSourceProcessSheet resources) {
        Optional<OutSourceProcessSheet> optionalSOutSourceProcessSheet = outSourceProcessSheetRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalSOutSourceProcessSheet,"SOutSourceProcessSheet","id",resources.getId());
        OutSourceProcessSheet outSourceProcessSheet = optionalSOutSourceProcessSheet.get();
        outSourceProcessSheet.copy(resources);
        outSourceProcessSheetRepository.save(outSourceProcessSheet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        outSourceProcessSheetRepository.deleteById(id);
    }
}