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
package me.zhengjie.mybatis.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Optional;
import java.util.Set;

/**
 * Mybatis全局缓存工具类
 *
 * @author Caratacus
 * @author liaojinlong
 * @since 2020/6/29 18:12
 */
public class GlobalConfigUtils {

    /**
     * 获取当前的SqlSessionFactory
     *
     * @param clazz 实体类
     */
    public static SqlSessionFactory currentSessionFactory(Class<?> clazz) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
        Assert.notNull(tableInfo, ClassUtils.getUserClass(clazz).getName() + " Not Found TableInfoCache.");
        return getGlobalConfig(tableInfo.getConfiguration()).getSqlSessionFactory();
    }

    /**
     * 获取默认 MybatisGlobalConfig
     * <p>FIXME 这可能是一个伪装成单例模式的原型模式，暂时不确定</p>
     */
    public static GlobalConfig defaults() {
        return new GlobalConfig().setDbConfig(new GlobalConfig.DbConfig());
    }

    /**
     * 获取MybatisGlobalConfig (统一所有入口)
     *
     * @param configuration Mybatis 容器配置对象
     */
    public static GlobalConfig getGlobalConfig(Configuration configuration) {
        Assert.notNull(configuration, "Error: You need Initialize MybatisConfiguration !");
        return ((MybatisConfiguration) configuration).getGlobalConfig();
    }

    public static IKeyGenerator getKeyGenerator(Configuration configuration) {
        return getGlobalConfig(configuration).getDbConfig().getKeyGenerator();
    }

    public static IdType getIdType(Configuration configuration) {
        return getGlobalConfig(configuration).getDbConfig().getIdType();
    }

    public static ISqlInjector getSqlInjector(Configuration configuration) {
        return getGlobalConfig(configuration).getSqlInjector();
    }

    public static Optional<MetaObjectHandler> getMetaObjectHandler(Configuration configuration) {
        return Optional.ofNullable(getGlobalConfig(configuration).getMetaObjectHandler());
    }

    public static Class<?> getSuperMapperClass(Configuration configuration) {
        return getGlobalConfig(configuration).getSuperMapperClass();
    }

    public static boolean isSupperMapperChildren(Configuration configuration, Class<?> mapperClass) {
        return getSuperMapperClass(configuration).isAssignableFrom(mapperClass);
    }

    public static Set<String> getMapperRegistryCache(Configuration configuration) {
        return getGlobalConfig(configuration).getMapperRegistryCache();
    }
}
