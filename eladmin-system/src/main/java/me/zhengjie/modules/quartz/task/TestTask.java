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
package me.zhengjie.modules.quartz.task;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.exception.TaskException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 测试用
 * @author Zheng Jie
 * @date 2019-01-08
 */
@Slf4j
@Async
@Component
@SuppressWarnings({"unused"})
public class TestTask {

    @SuppressWarnings({"unused"})
    public void run(){
        log.info("run 执行成功");
    }

    @SuppressWarnings({"unused"})
    public void run1(String str){
        log.info("run1 执行成功，参数为： {}" + str);
    }

    @SuppressWarnings({"unused"})
    public void run2(){
        log.info("run2 执行成功");
    }

    @SuppressWarnings({"unused"})
    public void runWithException() {
        throw new TaskException("返回一个测试错误");
    }

    @SuppressWarnings({"unused"})
    public void runWithException(String param) {
        throw new TaskException("返回一个带参数的测试错误", param);
    }
}
