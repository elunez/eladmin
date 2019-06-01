package me.zhengjie.utils.thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author wuyang
 */
public class LocalExecutorManager {
    public static final String EXECUTOR_VERIFICATION_CODE = "verification_code";
    public static final String WEB_SOCKET_CONFIG = "web_socket_config";
    public static final String XPAY_COMMON = "xpay_common";
    private static ConcurrentHashMap<String, LocalExecutor> hashMap = null;
    private static ConcurrentHashMap<String, ScheduledThreadPoolExecutor> hashMapSchedule = null;

    private synchronized static void init() {
        hashMap = new ConcurrentHashMap<>();
        hashMap.put(WEB_SOCKET_CONFIG, new LocalExecutor(WEB_SOCKET_CONFIG, 2, 100));
        hashMap.put(XPAY_COMMON, new LocalExecutor(XPAY_COMMON, 5, 100));
//        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//        service.scheduleAtFixedRate(new ExecutorMonitor(hashMap), 1, 60, TimeUnit.SECONDS);
    }

    private synchronized static void initSchedule() {
        hashMapSchedule = new ConcurrentHashMap<>();
        hashMapSchedule.put(EXECUTOR_VERIFICATION_CODE, new ScheduledThreadPoolExecutor(1, new MyThreadFactory(EXECUTOR_VERIFICATION_CODE)));
    }

    public static ExecutorService getExecutorService() {
        if (null == hashMap) {
            init();
        }
        return hashMap.get(XPAY_COMMON).getExecutorService();
    }

    public static ExecutorService getExecutorService(String name) {
        if (null == hashMap) {
            init();
        }
        if (null != hashMap && hashMap.containsKey(name)) {
            return hashMap.get(name).getExecutorService();
        }
        return hashMap.get(XPAY_COMMON).getExecutorService();
    }

    public static ScheduledThreadPoolExecutor getScheduledExecutorService(String name) {
        if (null == hashMapSchedule) {
            initSchedule();
        }
        if (null != hashMapSchedule && hashMapSchedule.containsKey(name)) {
            return hashMapSchedule.get(name);
        }
        return null;
    }
}
