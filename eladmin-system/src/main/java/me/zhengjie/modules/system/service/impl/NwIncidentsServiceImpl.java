package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.NwIncidents;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.system.repository.NwIncidentsRepository;
import me.zhengjie.modules.system.service.NwIncidentsService;
import me.zhengjie.modules.system.service.dto.NwIncidentsDTO;
import me.zhengjie.modules.system.service.dto.NwIncidentsQueryCriteria;
import me.zhengjie.modules.system.service.mapper.NwIncidentsMapper;
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
* @author Bobby Kimutai
* @date 2019-08-05
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class NwIncidentsServiceImpl implements NwIncidentsService {

    @Autowired
    private NwIncidentsRepository nwIncidentsRepository;

    @Autowired
    private NwIncidentsMapper nwIncidentsMapper;

    @Override
    public Object queryAll(NwIncidentsQueryCriteria criteria, Pageable pageable){
        Page<NwIncidents> page = nwIncidentsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(nwIncidentsMapper::toDto));
    }

    @Override
    public Object queryAll(NwIncidentsQueryCriteria criteria){
        return nwIncidentsMapper.toDto(nwIncidentsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public NwIncidentsDTO findById(Integer id) {
        Optional<NwIncidents> nwIncidents = nwIncidentsRepository.findById(id);
        ValidationUtil.isNull(nwIncidents,"NwIncidents","id",id);
        return nwIncidentsMapper.toDto(nwIncidents.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NwIncidentsDTO create(NwIncidents resources) {
        return nwIncidentsMapper.toDto(nwIncidentsRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(NwIncidents resources) {
        Optional<NwIncidents> optionalNwIncidents = nwIncidentsRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalNwIncidents,"NwIncidents","id",resources.getId());
        NwIncidents nwIncidents = optionalNwIncidents.get();
        nwIncidents.copy(resources);
        nwIncidentsRepository.save(nwIncidents);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        nwIncidentsRepository.deleteById(id);
    }
}