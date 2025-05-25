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
package me.zhengjie.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Zheng Jie
 * @date 2019-6-4 13:52:30
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

    // Dong ZhaoYang 2017/8/7 Property name of the basic object
    String propName() default "";
    // Dong ZhaoYang 2017/8/7 Query type
    Type type() default Type.EQUAL;

    /**
     * Property name for join query, e.g., dept in User class
     */
    String joinName() default "";

    /**
     * Default left join
     */
    Join join() default Join.LEFT;

    /**
     * Multi-field fuzzy search, only supports String type fields, separated by commas, e.g., @Query(blurry = "email,username")
     */
    String blurry() default "";

    enum Type {
        // jie 2019/6/4 Equal
        EQUAL
        // Dong ZhaoYang 2017/8/7 Greater than or equal to
        , GREATER_THAN
        // Dong ZhaoYang 2017/8/7 Less than or equal to
        , LESS_THAN
        // Dong ZhaoYang 2017/8/7 Inner fuzzy query
        , INNER_LIKE
        // Dong ZhaoYang 2017/8/7 Left fuzzy query
        , LEFT_LIKE
        // Dong ZhaoYang 2017/8/7 Right fuzzy query
        , RIGHT_LIKE
        // Dong ZhaoYang 2017/8/7 Less than
        , LESS_THAN_NQ
        // jie 2019/6/4 Contains
        , IN
        // Not contains
        , NOT_IN
        // Not equal
        ,NOT_EQUAL
        // between
        ,BETWEEN
        // Not null
        ,NOT_NULL
        // Is null
        ,IS_NULL,
        // Aborn Jiang 2022/06/01, Corresponds to SQL: SELECT * FROM table WHERE FIND_IN_SET('querytag', table.tags);
        FIND_IN_SET
    }

    /**
     * @author Zheng Jie
     * Suitable for simple join queries. For complex cases, please customize this annotation or use SQL queries.
     */
    enum Join {
        /** jie 2019-6-4 13:18:30 */
        LEFT, RIGHT, INNER
    }

}
