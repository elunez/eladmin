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
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * MyBatis & JPA  查询适配
 *
 * @author liaojinlong
 * @since 2020/7/2 19:18
 */
public class ElPredicate<T> implements Predicate<T> {
    private Predicate<T> predicate;
    private QueryWrapper queryWrapper;

    public ElPredicate(Predicate<T> predicate, QueryWrapper queryWrapper) {
        this.predicate = predicate;
        this.queryWrapper = queryWrapper;
    }


    /**
     * Evaluates this predicate on the given argument.
     *
     * @param o the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    @Override
    public boolean test(T o) {
        return predicate.test(o);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * AND of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code false}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ANDed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * AND of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    @Override
    public Predicate and(Predicate other) {
        return predicate.and(other);
    }

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     *
     * @return a predicate that represents the logical negation of this
     * predicate
     */
    @Override
    public Predicate negate() {
        return predicate.negate();
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * OR of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code true}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * OR of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    @Override
    public Predicate or(Predicate other) {
        return predicate.or(other);
    }

    public static synchronized <T> List<ElPredicate<T>> toConvert(List<Predicate<T>> predicates, List<QueryWrapper<T>> queryWrappers) {
        Assert.isTrue(predicates.size() == queryWrappers.size(), "二者数量必须匹配");
        List<ElPredicate<T>> result = new ArrayList<>(queryWrappers.size());
        for (int i = 0; i < predicates.size(); i++) {
            Predicate<T> tPredicate = predicates.get(i);
            QueryWrapper<T> queryWrapper = queryWrappers.get(i);
            result.add(toConvert(tPredicate, queryWrapper));
        }
        return result;
    }

    public static synchronized <T> ElPredicate<T> toConvert(Predicate<T> predicate, QueryWrapper<T> queryWrapper) {
        return new ElPredicate<T>(predicate, queryWrapper);
    }
}

