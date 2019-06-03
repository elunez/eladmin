package me.zhengjie.service.query;

import me.zhengjie.domain.QiniuContent;
import me.zhengjie.repository.QiniuContentRepository;
import me.zhengjie.utils.BeanHelp;
import me.zhengjie.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
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
 * @date 2018-12-31
 */
@Service
@CacheConfig(cacheNames = "qiNiu")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QiNiuQueryService {

    @Autowired
    private QiniuContentRepository qiniuContentRepository;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(QiniuContent qiniuContent, Pageable pageable){
        return PageUtil.toPage(qiniuContentRepository.findAll((root, query, cb) -> BeanHelp.getPredicate(root, qiniuContent, cb), pageable));
    }

}
