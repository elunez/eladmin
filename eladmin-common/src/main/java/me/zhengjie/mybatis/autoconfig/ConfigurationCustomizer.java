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

package me.zhengjie.mybatis.autoconfig;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.apache.ibatis.session.Configuration;

/**
 * Callback interface that can be customized a {@link MybatisConfiguration} object generated on auto-configuration.
 *
 * @author Kazuki Shimizu
 * @author liaojinlong
 * @since 2020/6/29 18:08
 */
@FunctionalInterface
public interface ConfigurationCustomizer {

    /**
     * Customize the given a {@link MybatisConfiguration} object.
     *
     * @param configuration the configuration object to customize
     */
    void customize(Configuration configuration);
}
