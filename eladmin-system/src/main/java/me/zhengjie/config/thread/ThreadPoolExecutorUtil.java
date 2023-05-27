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
package me.zhengjie.config.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 用于获取自定义线程池
 * @author Zheng Jie
 * @date 2019年10月31日18:16:47
 */
public class ThreadPoolExecutorUtil {

    public static ExecutorService getPoll() {
        return getPoll(null);
    }

    public static ExecutorService getPoll(String threadName) {
        return new ThreadPoolExecutor(AsyncTaskProperties.corePoolSize, AsyncTaskProperties.maxPoolSize, AsyncTaskProperties.keepAliveSeconds, TimeUnit.SECONDS, new ArrayBlockingQueue<>(AsyncTaskProperties.queueCapacity), new TheadFactoryName(threadName), // 队列与线程池中线程都满了时使用调用者所在的线程来执行
        new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
