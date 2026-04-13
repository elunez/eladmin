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
package me.zhengjie.utils.gencode;

import java.util.HashMap;
import java.util.Map;

public final class FilePathStrategyFactory {

    private static final Map<String, FilePathStrategy> ADMIN_STRATEGY_MAP = new HashMap<>();
    private static final Map<String, FrontFilePathStrategy> FRONT_STRATEGY_MAP = new HashMap<>();

    static {
        registerAdminStrategy(new EntityFilePathStrategy());
        registerAdminStrategy(new ControllerFilePathStrategy());
        registerAdminStrategy(new ServiceFilePathStrategy());
        registerAdminStrategy(new ServiceImplFilePathStrategy());
        registerAdminStrategy(new DtoFilePathStrategy());
        registerAdminStrategy(new QueryCriteriaFilePathStrategy());
        registerAdminStrategy(new MapperFilePathStrategy());
        registerAdminStrategy(new RepositoryFilePathStrategy());

        registerFrontStrategy(new ApiFrontFilePathStrategy());
        registerFrontStrategy(new IndexFrontFilePathStrategy());
    }

    private FilePathStrategyFactory() {
    }

    private static void registerAdminStrategy(FilePathStrategy strategy) {
        ADMIN_STRATEGY_MAP.put(strategy.getTemplateName(), strategy);
    }

    private static void registerFrontStrategy(FrontFilePathStrategy strategy) {
        FRONT_STRATEGY_MAP.put(strategy.getTemplateName(), strategy);
    }

    public static FilePathStrategy getAdminStrategy(String templateName) {
        return ADMIN_STRATEGY_MAP.get(templateName);
    }

    public static FrontFilePathStrategy getFrontStrategy(String templateName) {
        return FRONT_STRATEGY_MAP.get(templateName);
    }
}
