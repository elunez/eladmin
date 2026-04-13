package me.zhengjie.utils.query;

import me.zhengjie.annotation.Query;

import java.util.EnumMap;
import java.util.Map;

public class JoinStrategyFactory {

    private static final Map<Query.Join, JoinStrategy> STRATEGIES = new EnumMap<>(Query.Join.class);

    static {
        STRATEGIES.put(Query.Join.LEFT, new LeftJoinStrategy());
        STRATEGIES.put(Query.Join.RIGHT, new RightJoinStrategy());
        STRATEGIES.put(Query.Join.INNER, new InnerJoinStrategy());
    }

    public static JoinStrategy getStrategy(Query.Join joinType) {
        return STRATEGIES.get(joinType);
    }
}
