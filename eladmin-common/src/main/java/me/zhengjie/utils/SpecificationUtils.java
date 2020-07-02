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
package me.zhengjie.utils;

import me.zhengjie.db.ElSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author liaojinlong
 * @since 2020/7/2 19:44
 */
public class SpecificationUtils {
    public static synchronized <T> ElSpecification getSpecification() {
        return new ElSpecification<T>() {
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
                return QueryHelp.getPredicate(root, query, criteriaBuilder);
            }
        };


    }
}
