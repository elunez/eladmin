package me.zhengjie.modules.system.service.strategy;

import me.zhengjie.utils.enums.DataScopeEnum;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataScopeStrategyContext {
    
    private final List<DataScopeStrategy> strategies;
    private final Map<String, DataScopeStrategy> strategyMap = new HashMap<>();
    
    public DataScopeStrategyContext(List<DataScopeStrategy> strategies) {
        this.strategies = strategies;
    }
    
    @PostConstruct
    public void init() {
        for (DataScopeStrategy strategy : strategies) {
            strategyMap.put(strategy.getScopeType(), strategy);
        }
    }
    
    public DataScopeStrategy getStrategy(String scopeType) {
        DataScopeStrategy strategy = strategyMap.get(scopeType);
        if (strategy == null) {
            return strategyMap.get(DataScopeEnum.ALL.getValue());
        }
        return strategy;
    }
    
    public boolean containsStrategy(String scopeType) {
        return strategyMap.containsKey(scopeType);
    }
}
