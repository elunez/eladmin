package me.zhengjie.service.query;

import me.zhengjie.domain.Picture;
import me.zhengjie.repository.PictureRepository;
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
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "picture")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PictureQueryService {

    @Autowired
    private PictureRepository pictureRepository;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(Picture picture, Pageable pageable){
        return PageUtil.toPage(pictureRepository.findAll(new Spec(picture),pageable));
    }

    class Spec implements Specification<Picture> {

        private Picture picture;

        public Spec(Picture picture){
            this.picture = picture;
        }

        @Override
        public Predicate toPredicate(Root<Picture> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(picture.getFilename())){
                /**
                 * 模糊
                 */
                list.add(cb.like(root.get("filename").as(String.class),"%"+picture.getFilename()+"%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
