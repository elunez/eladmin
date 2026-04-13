package me.zhengjie.utils.query;

import me.zhengjie.annotation.Query;

import java.util.EnumMap;
import java.util.Map;

public class PredicateStrategyFactory {

    private static final Map<Query.Type, PredicateStrategy> STRATEGIES = new EnumMap<>(Query.Type.class);

    static {
        STRATEGIES.put(Query.Type.EQUAL, new EqualStrategy());
        STRATEGIES.put(Query.Type.GREATER_THAN, new GreaterThanStrategy());
        STRATEGIES.put(Query.Type.LESS_THAN, new LessThanStrategy());
        STRATEGIES.put(Query.Type.LESS_THAN_NQ, new LessThanNqStrategy());
        STRATEGIES.put(Query.Type.INNER_LIKE, new InnerLikeStrategy());
        STRATEGIES.put(Query.Type.LEFT_LIKE, new LeftLikeStrategy());
        STRATEGIES.put(Query.Type.RIGHT_LIKE, new RightLikeStrategy());
        STRATEGIES.put(Query.Type.IN, new InStrategy());
        STRATEGIES.put(Query.Type.NOT_IN, new NotInStrategy());
        STRATEGIES.put(Query.Type.NOT_EQUAL, new NotEqualStrategy());
        STRATEGIES.put(Query.Type.NOT_NULL, new NotNullStrategy());
        STRATEGIES.put(Query.Type.IS_NULL, new IsNullStrategy());
        STRATEGIES.put(Query.Type.BETWEEN, new BetweenStrategy());
        STRATEGIES.put(Query.Type.FIND_IN_SET, new FindInSetStrategy());
    }

    public static PredicateStrategy getStrategy(Query.Type type) {
        return STRATEGIES.get(type);
    }
}
