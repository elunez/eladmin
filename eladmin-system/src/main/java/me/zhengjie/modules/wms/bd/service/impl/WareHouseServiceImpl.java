package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.SupplierInfo;
import me.zhengjie.modules.wms.bd.domain.WareHouse;
import me.zhengjie.modules.wms.bd.repository.WareHouseRepository;
import me.zhengjie.modules.wms.bd.service.WareHouseService;
import me.zhengjie.modules.wms.bd.service.dto.WareHouseDTO;
import me.zhengjie.modules.wms.bd.service.dto.WareHouseQueryCriteria;
import me.zhengjie.modules.wms.bd.service.mapper.WareHouseMapper;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * @date 2019-07-27
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WareHouseServiceImpl implements WareHouseService {

    @Autowired
    private WareHouseMapper wareHouseMapper;

    @Autowired
    private WareHouseRepository wareHouseRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WareHouseDTO create(WareHouse resources) {
        //验证仓库编码或者仓库名字是否存在
        List<WareHouse> wareHouseList = wareHouseRepository.findByNameOrWareHouseCodeAndStatusTrue(resources.getName(), resources.getWareHouseCode());
        if(!CollectionUtils.isEmpty(wareHouseList)) {
            throw new BadRequestException("仓库编码或编号已经存在");
        }

        WareHouse wareHouseDelete = wareHouseRepository.findByNameAndWareHouseCodeAndStatusFalse(resources.getName(), resources.getWareHouseCode());
        if(null != wareHouseDelete){
            wareHouseDelete.setStatus(true);
            wareHouseRepository.updateStatusTrue(wareHouseDelete.getId());
            return wareHouseMapper.toDto(wareHouseDelete);

        }
        resources.setStatus(true);
        return wareHouseMapper.toDto(wareHouseRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WareHouseDTO findById(long id) {
        Optional<WareHouse> wareHouse = wareHouseRepository.findById(id);
        ValidationUtil.isNull(wareHouse,"wareHouse","id",id);
        return wareHouseMapper.toDto(wareHouse.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(long id) {
        Optional<WareHouse> wareHouseOptional  = wareHouseRepository.findById(id);
        WareHouse wareHouse = wareHouseOptional.get();
        wareHouse.setStatus(false);
        wareHouseRepository.deleteWareHouse(id);
    }

    @Override
    public Object queryAll(WareHouseQueryCriteria wareHouseQueryCriteria, Pageable pageable) {
        Specification<WareHouse> specification = new Specification<WareHouse>() {
            @Override
            public Predicate toPredicate(Root<WareHouse> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);

                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };
        Page<WareHouse> page = wareHouseRepository.findAll(specification, pageable);
        return PageUtil.toPage(page.map(wareHouseMapper::toDto));
    }

    @Override
    public Object queryAll(WareHouseQueryCriteria wareHouseQueryCriteria) {
        Specification<WareHouse> specification = new Specification<WareHouse>() {
            @Override
            public Predicate toPredicate(Root<WareHouse> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);

                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };
        List<WareHouse> wareHouseList = wareHouseRepository.findAll(specification);
        return wareHouseList;
    }

}
