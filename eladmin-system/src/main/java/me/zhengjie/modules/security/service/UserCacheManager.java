package me.zhengjie.modules.security.service;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.security.service.dto.JwtUserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 用户缓存
 *
 * @author TikiWong
 * @date 2022/1/27 8:23
 **/
@Slf4j
@Component
public class UserCacheManager {

    @Value("${user-cache.min-evictable-size}")
    private int minEvictableSize;
    @Value("${user-cache.min-evictable-interval}")
    private long minEvictableInterval;
    @Value("${user-cache.min-idle-time}")
    private long minIdleTime;

    private final Map<String, Node> cache = new ConcurrentHashMap<>();
    private final AtomicBoolean expelLock = new AtomicBoolean(true);
    private long nextMinEvictableTime = 0;

    public Future<JwtUserDto> putIfAbsent(String username, Future<JwtUserDto> ft) {
        Node tryNode = new Node(ft);
        Node node = cache.putIfAbsent(username, tryNode);
        expel();
        return nodeToDate(node);
    }

    /**
     * 缓存回收
     * 为避免超过边界后回收热点数据设置了最小生存时间
     * 回收时会保留在最小生存时间内的数据
     **/
    public void expel() {
        long now = System.currentTimeMillis();
        if (cache.size() < minEvictableSize ||
                now < nextMinEvictableTime ||
                !expelLock.compareAndSet(true, false)) {
            return;
        }
        long oldestTime = now;
        int evictedCount = 0;
        try {
            Iterator<Map.Entry<String, Node>> iterator = cache.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Node> entry = iterator.next();
                long nodeTime = entry.getValue().getTime();
                if (nodeTime + minIdleTime < now) {
                    iterator.remove();
                    evictedCount++;
                }
                oldestTime = Math.min(oldestTime, nodeTime);
            }
        } finally {
            this.nextMinEvictableTime = Math.max(now + minEvictableInterval, oldestTime);
            expelLock.set(true);
            log.info("回收掉【{}】条用户缓存, 剩余缓存数为【{}】,下次可回收时间为【{}】秒后",
                    evictedCount,
                    cache.size(),
                    (this.nextMinEvictableTime - now) / 1000);
        }
    }

    public Future<JwtUserDto> get(String username) {
        return nodeToDate(cache.get(username));
    }

    public void clear() {
        cache.clear();
    }

    public void remove(String username) {
        cache.remove(username);
    }

    private Future<JwtUserDto> nodeToDate(Node node) {
        return node == null ? null : node.getData();
    }

    private static class Node {
        private final Future<JwtUserDto> data;
        private final long time;

        public Node(Future<JwtUserDto> data) {
            this.data = data;
            this.time = System.currentTimeMillis();
        }

        public Future<JwtUserDto> getData() {
            return data;
        }

        public long getTime() {
            return time;
        }
    }
}
