package me.zhengjie.utils.gen;

import java.util.HashMap;
import java.util.Map;

public class FilePathStrategyFactory {

    private static final Map<String, FilePathStrategy> ADMIN_STRATEGIES = new HashMap<>();

    static {
        ADMIN_STRATEGIES.put("Entity", new EntityFilePathStrategy());
        ADMIN_STRATEGIES.put("Controller", new ControllerFilePathStrategy());
        ADMIN_STRATEGIES.put("Service", new ServiceFilePathStrategy());
        ADMIN_STRATEGIES.put("ServiceImpl", new ServiceImplFilePathStrategy());
        ADMIN_STRATEGIES.put("Dto", new DtoFilePathStrategy());
        ADMIN_STRATEGIES.put("QueryCriteria", new QueryCriteriaFilePathStrategy());
        ADMIN_STRATEGIES.put("Mapper", new MapperFilePathStrategy());
        ADMIN_STRATEGIES.put("Repository", new RepositoryFilePathStrategy());
    }

    public static FilePathStrategy getAdminStrategy(String templateName) {
        return ADMIN_STRATEGIES.get(templateName);
    }

    private static final Map<String, FrontFilePathStrategy> FRONT_STRATEGIES = new HashMap<>();

    static {
        FRONT_STRATEGIES.put("api", new ApiFilePathStrategy());
        FRONT_STRATEGIES.put("index", new IndexFilePathStrategy());
    }

    public static FrontFilePathStrategy getFrontStrategy(String templateName) {
        return FRONT_STRATEGIES.get(templateName);
    }
}
