package me.zhengjie.modules.system.service.datascope;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.service.DeptService;
import me.zhengjie.utils.enums.DataScopeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataScopeStrategyFactory {

    private final DeptService deptService;
    private final Map<DataScopeEnum, DataScopeStrategy> strategies = new EnumMap<>(DataScopeEnum.class);

    @PostConstruct
    public void init() {
        strategies.put(DataScopeEnum.THIS_LEVEL, new ThisLevelStrategy());
        strategies.put(DataScopeEnum.CUSTOMIZE, new CustomizeStrategy(deptService));
    }

    public DataScopeStrategy getStrategy(DataScopeEnum dataScopeEnum) {
        return strategies.get(dataScopeEnum);
    }
}
