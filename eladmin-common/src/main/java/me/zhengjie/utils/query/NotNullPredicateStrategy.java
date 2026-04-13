/*
 *  Copyright 2019-2025 Zheng Jie
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
package me.zhengjie.utils.query;

import me.zhengjie.annotation.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NotNullPredicateStrategy implements QueryPredicateStrategy {

    @Override
    public Query.Type getQueryType() {
        return Query.Type.NOT_NULL;
    }

    @Override
    public Predicate createPredicate(CriteriaBuilder cb, Expression<?> expression, 
                                      Class<?> fieldType, Object value, 
                                      Root<?> root, String attributeName) {
        return cb.isNotNull(expression);
    }
}
