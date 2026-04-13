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

import java.util.HashMap;
import java.util.Map;

public final class QueryPredicateStrategyFactory {

    private static final Map<Query.Type, QueryPredicateStrategy> STRATEGY_MAP = new HashMap<>();

    static {
        registerStrategy(new EqualPredicateStrategy());
        registerStrategy(new GreaterThanPredicateStrategy());
        registerStrategy(new LessThanPredicateStrategy());
        registerStrategy(new LessThanNqPredicateStrategy());
        registerStrategy(new InnerLikePredicateStrategy());
        registerStrategy(new LeftLikePredicateStrategy());
        registerStrategy(new RightLikePredicateStrategy());
        registerStrategy(new InPredicateStrategy());
        registerStrategy(new NotInPredicateStrategy());
        registerStrategy(new NotEqualPredicateStrategy());
        registerStrategy(new NotNullPredicateStrategy());
        registerStrategy(new IsNullPredicateStrategy());
        registerStrategy(new BetweenPredicateStrategy());
        registerStrategy(new FindInSetPredicateStrategy());
    }

    private QueryPredicateStrategyFactory() {
    }

    private static void registerStrategy(QueryPredicateStrategy strategy) {
        STRATEGY_MAP.put(strategy.getQueryType(), strategy);
    }

    public static QueryPredicateStrategy getStrategy(Query.Type type) {
        return STRATEGY_MAP.get(type);
    }
}
