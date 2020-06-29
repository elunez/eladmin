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

import com.baomidou.mybatisplus.core.MybatisXMLConfigBuilder;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.injector.SqlRunnerInjector;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * 重写SqlSessionFactoryBuilder
 *
 * @author nieqiurong 2019/2/23.
 */
public class MybatisSqlSessionFactoryBuilder extends SqlSessionFactoryBuilder {

    @SuppressWarnings("Duplicates")
    @Override
    public SqlSessionFactory build(Reader reader, String environment, Properties properties) {
        try {
            //TODO 这里换成 MybatisXMLConfigBuilder 而不是 XMLConfigBuilder
            MybatisXMLConfigBuilder parser = new MybatisXMLConfigBuilder(reader, environment, properties);
            return build(parser.parse());
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error building SqlSession.", e);
        } finally {
            ErrorContext.instance().reset();
            try {
                reader.close();
            } catch (IOException e) {
                // Intentionally ignore. Prefer previous error.
            }
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {
        try {
            //TODO 这里换成 MybatisXMLConfigBuilder 而不是 XMLConfigBuilder
            MybatisXMLConfigBuilder parser = new MybatisXMLConfigBuilder(inputStream, environment, properties);
            return build(parser.parse());
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error building SqlSession.", e);
        } finally {
            ErrorContext.instance().reset();
            try {
                inputStream.close();
            } catch (IOException e) {
                // Intentionally ignore. Prefer previous error.
            }
        }
    }

    // TODO 使用自己的逻辑,注入必须组件
    @Override
    public SqlSessionFactory build(Configuration config) {
        MybatisConfiguration configuration = (MybatisConfiguration) config;
        GlobalConfig globalConfig = GlobalConfigUtils.getGlobalConfig(configuration);
        final IdentifierGenerator identifierGenerator;
        if (globalConfig.getIdentifierGenerator() == null) {
            if (null != globalConfig.getWorkerId() && null != globalConfig.getDatacenterId()) {
                identifierGenerator = new DefaultIdentifierGenerator(globalConfig.getWorkerId(), globalConfig.getDatacenterId());
            } else {
                identifierGenerator = new DefaultIdentifierGenerator();
            }
            globalConfig.setIdentifierGenerator(identifierGenerator);
        } else {
            identifierGenerator = globalConfig.getIdentifierGenerator();
        }
        //TODO 这里只是为了兼容下,并没多大重要,方法标记过时了.
        IdWorker.setIdentifierGenerator(identifierGenerator);

        if (globalConfig.isEnableSqlRunner()) {
            new SqlRunnerInjector().inject(configuration);
        }

        SqlSessionFactory sqlSessionFactory = super.build(configuration);

        // 缓存 sqlSessionFactory
        globalConfig.setSqlSessionFactory(sqlSessionFactory);

        return sqlSessionFactory;
    }
}
