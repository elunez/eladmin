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
package me.zhengjie.config;

import cn.hutool.core.util.StrUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zheng Jie
 * @description 自定义 p6spy sql输出格式
 * @date 2024-12-26
 **/
@Slf4j
public class CustomP6SpyLogger implements MessageFormattingStrategy {

    // 重置颜色
    private static final String RESET = "\u001B[0m";
    // 红色
    private static final String RED = "\u001B[31m";
    // 绿色
    private static final String GREEN = "\u001B[32m";

    /**
     * 格式化 sql
     * @param connectionId 连接id
     * @param now 当前时间
     * @param elapsed 执行时长
     * @param category sql分类
     * @param prepared 预编译sql
     * @param sql 执行sql
     * @param url 数据库连接url
     * @return 格式化后的sql
     */
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        // 去掉换行和多余空格
        if(StrUtil.isNotBlank(sql)){
            sql = sql.replaceAll("\\s+", " ").trim();
        }

        // 格式化并加上颜色
        return String.format(
                "%s[Time: %dms]%s - %s%s%s;",
                GREEN, elapsed, RESET, RED, sql, RESET
        );
    }
}

