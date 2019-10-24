package me.zhengjie.modules.monitor.service.impl;

import me.zhengjie.modules.monitor.domain.vo.RedisVo;
import me.zhengjie.modules.monitor.service.RedisService;
import me.zhengjie.utils.PageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Zheng Jie
 * @date 2018-12-10
 */
@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate redisTemplate;

    @Value("${loginCode.expiration}")
    private Long expiration;

    public RedisServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<RedisVo> findByKey(String key, Pageable pageable){
        List<RedisVo> redisVos = new ArrayList<>();
        if(!"*".equals(key)){
            key = "*" + key + "*";
        }
        for (Object s : Objects.requireNonNull(redisTemplate.keys(key))) {
            // 过滤掉权限的缓存
            if (s.toString().contains("role::loadPermissionByUser") || s.toString().contains("user::loadUserByUsername")) {
                continue;
            }
            RedisVo redisVo = new RedisVo(s.toString(), Objects.requireNonNull(redisTemplate.opsForValue().get(s.toString())).toString());
            redisVos.add(redisVo);
        }
        return new PageImpl<RedisVo>(
                PageUtil.toPage(pageable.getPageNumber(),pageable.getPageSize(),redisVos),
                pageable,
                redisVos.size());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void flushdb() {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushDb();
    }

    @Override
    public String getCodeVal(String key) {
        try {
            return Objects.requireNonNull(redisTemplate.opsForValue().get(key)).toString();
        }catch (Exception e){
            return "";
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveCode(String key, Object val) {
        redisTemplate.opsForValue().set(key,val);
        redisTemplate.expire(key,expiration, TimeUnit.MINUTES);
    }
}
