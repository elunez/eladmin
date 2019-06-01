package me.zhengjie.utils.thread;

import java.util.concurrent.*;

public class LocalExecutor {
    private String name;

    private int poolSize;

    private int blockQueueWarningSize;

    private ExecutorService executorService = null;

    public LocalExecutor(String name, int poolSize, int blockQueueWarningSize) {
        this.name = name;
        this.poolSize = poolSize;
        this.blockQueueWarningSize = blockQueueWarningSize;
        //Get the ThreadFactory implementation to use
//        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ThreadFactory threadFactory = new MyThreadFactory(name);
        this.executorService = new ThreadPoolExecutor(1, poolSize,
                10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(blockQueueWarningSize), threadFactory);
    }

    public String getName() {
        return name;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public int getBlockQueueWarningSize() {
        return blockQueueWarningSize;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

}

