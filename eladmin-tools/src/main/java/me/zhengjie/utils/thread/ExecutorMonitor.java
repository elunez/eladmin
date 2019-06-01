package me.zhengjie.utils.thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorMonitor implements Runnable {
    private ConcurrentHashMap<String, LocalExecutor> hashMap = null;

    public ExecutorMonitor(ConcurrentHashMap<String, LocalExecutor> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public void run() {
        for (ConcurrentHashMap.Entry<String, LocalExecutor> items : hashMap.entrySet()) {
            LocalExecutor executor = items.getValue();
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor.getExecutorService();
            if (threadPoolExecutor.getQueue().size() > executor.getBlockQueueWarningSize()) {
                String title = "线程超过阈值提醒";
                String content = "book线程超过100提醒,请登陆后台查看!" + ", Thread name:" + executor.getName() + ",  Thread max:" + executor.getPoolSize()
                        + ", Thread queue:" + threadPoolExecutor.getQueue().size() + ", Exceed warning thread:" + executor.getBlockQueueWarningSize();
                //SmsUtils.sendEmail(title, content);

            }
        }
    }
}
