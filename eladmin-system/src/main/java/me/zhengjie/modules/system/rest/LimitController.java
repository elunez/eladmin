/*
 *  Copyright 2019-2020 Zheng Jie
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
package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Limit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author /
 * 接口限流测试类
 */
@RestController
@RequestMapping("/api/limit")
@Api(tags = "系统：限流测试管理")
public class LimitController {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    /**
     * 测试限流注解，下面配置说明该接口 60秒内最多只能访问 10次，保存到redis的键名为 limit_test，
     */
    @GetMapping
    @AnonymousAccess
    @ApiOperation("测试")
    @Limit(key = "test", period = 60, count = 10, name = "testLimit", prefix = "limit")
    public int test() {
        return ATOMIC_INTEGER.incrementAndGet();
    }
}
