/*
 *  Copyright 2019-2020
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.db;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.zhengjie.utils.QueryHelp;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author liaojinlong
 * @since 2020/7/2 19:39
 */
public class ElSpecification<K> implements Specification {
    private final Object criteria;
    private final Class<K> clazz;
    protected Specification<K> specification;

    public ElSpecification(Specification<K> specification, Object criteria, Class<K> clazz) {
        this.specification = specification;
        this.criteria = criteria;
        this.clazz = clazz;
    }

    public QueryWrapper<K> getQueryWrapper() {
        return QueryHelp.getQueryWrapper(criteria, clazz);
    }

    public Object getCriteria() {
        return criteria;
    }

    public Class<K> getClazz() {
        return clazz;
    }

    public Specification<K> getSpecification() {
        return specification;
    }

    public void setSpecification(Specification<K> specification) {
        this.specification = specification;
    }

    /**
     * Creates a WHERE clause for a query of the referenced entity in form of a {@link Predicate} for the given
     * {@link Root} and {@link CriteriaQuery}.
     *
     * @param root            must not be {@literal null}.
     * @param query           must not be {@literal null}.
     * @param criteriaBuilder must not be {@literal null}.
     * @return a {@link Predicate}, may be {@literal null}.
     */
    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        return specification.toPredicate(root, query, criteriaBuilder);
    }

    /**
     * ANDs the given {@link Specification} to the current one.
     *
     * @param other can be {@literal null}.
     * @return The conjunction of the specifications
     * @since 2.0
     */
    @Override
    public Specification and(Specification other) {
        return specification.and(other);
    }

    /**
     * ORs the given specification to the current one.
     *
     * @param other can be {@literal null}.
     * @return The disjunction of the specifications
     * @since 2.0
     */
    @Override
    public Specification or(Specification other) {
        return specification.or(specification);
    }
}
