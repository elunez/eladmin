package me.zhengjie.modules.system.service.query;

import me.zhengjie.utils.BeanHelp;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.system.domain.Dict;
import me.zhengjie.modules.system.service.dto.DictDTO;
import me.zhengjie.modules.system.repository.DictRepository;
import me.zhengjie.modules.system.service.mapper.DictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jie
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "dict")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictQueryService {

    @Autowired
    private DictRepository dictRepository;

    @Autowired
    private DictMapper dictMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(DictDTO dict, Pageable pageable){
        //Page<Dict> page = dictRepository.findAll(new Spec(dict),pageable);
        /** Dong ZhaoYang 2019/6/3 修改分页查询方法 */
        Page<Dict> page = dictRepository.findAll((root, query, cb) -> BeanHelp.getPredicate(root, dict, cb), pageable);
        return PageUtil.toPage(page.map(dictMapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(DictDTO dict){
        //return dictMapper.toDto(dictRepository.findAll(new Spec(dict)));
        /** Dong ZhaoYang 2019/6/3 不分页 同理 */
        return dictMapper.toDto(dictRepository.findAll((root, query, cb) -> BeanHelp.getPredicate(root, dict, cb)));
    }
}
