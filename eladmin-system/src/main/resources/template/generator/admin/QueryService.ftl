package ${package}.service.query;

import me.zhengjie.utils.PageUtil;
import ${package}.domain.${className};
import ${package}.service.dto.${className}DTO;
import ${package}.repository.${className}Repository;
import ${package}.service.mapper.${className}Mapper;
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
@CacheConfig(cacheNames = "${changeClassName}")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ${className}QueryService {

    @Autowired
    private ${className}Repository ${changeClassName}Repository;

    @Autowired
    private ${className}Mapper ${changeClassName}Mapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(${className}DTO ${changeClassName}, Pageable pageable){
        Page<${className}> page = ${changeClassName}Repository.findAll(new Spec(${changeClassName}),pageable);
        return PageUtil.toPage(page.map(${changeClassName}Mapper::toDto));
    }

    /**
    * 不分页
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(${className}DTO ${changeClassName}){
        return ${changeClassName}Mapper.toDto(${changeClassName}Repository.findAll(new Spec(${changeClassName})));
    }

    class Spec implements Specification<${className}> {

        private ${className}DTO ${changeClassName};

        public Spec(${className}DTO ${changeClassName}){
            this.${changeClassName} = ${changeClassName};
        }

        @Override
        public Predicate toPredicate(Root<${className}> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

<#if queryColumns??>
    <#list queryColumns as column>
            if(!ObjectUtils.isEmpty(${changeClassName}.get${column.capitalColumnName}())){
            <#if column.columnQuery = '1'>
                /**
                * 模糊
                */
                list.add(cb.like(root.get("${column.columnName}").as(${column.columnType}.class),"%"+${changeClassName}.get${column.capitalColumnName}()+"%"));
            </#if>
            <#if column.columnQuery = '2'>
                /**
                * 精确
                */
                list.add(cb.equal(root.get("${column.columnName}").as(${column.columnType}.class),${changeClassName}.get${column.capitalColumnName}()));
            </#if>
            }
    </#list>
</#if>
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
        }
    }
}