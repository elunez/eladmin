package me.zhengjie.modules.monitor.service.impl;

import me.zhengjie.modules.monitor.domain.vo.RedisVo;
import me.zhengjie.modules.monitor.service.RedisService;
import me.zhengjie.modules.security.config.SecurityProperties;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-12-10
 */
@Service
@SuppressWarnings({"unchecked","all"})
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate redisTemplate;
    private final SecurityProperties properties;
    @Value("${loginCode.expiration}")
    private Long expiration;

    public RedisServiceImpl(RedisTemplate redisTemplate, SecurityProperties properties) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;
    }

    @Override
    public Page<RedisVo> findByKey(String key, Pageable pageable){
        List<RedisVo> redisVos = findByKey(key);
        return new PageImpl<RedisVo>(
                PageUtil.toPage(pageable.getPageNumber(),pageable.getPageSize(),redisVos),
                pageable,
                redisVos.size());
    }

    @Override
    public List<RedisVo> findByKey(String key) {
        List<RedisVo> redisVos = new ArrayList<>();
        if(!"*".equals(key)){
            key = "*" + key + "*";
        }
        Set<String> keys = redisTemplate.keys(key);
        for (String s : keys) {
            // 过滤掉权限的缓存
            if (s.contains("role::loadPermissionByUser") || s.contains("user::loadUserByUsername") || s.contains(properties.getOnlineKey()) || s.contains(properties.getCodeKey())) {
                continue;
            }
            RedisVo redisVo = new RedisVo(s, Objects.requireNonNull(redisTemplate.opsForValue().get(s)).toString());
            redisVos.add(redisVo);
        }
        return redisVos;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void deleteAll() {
        Set<String> keys = redisTemplate.keys(  "*");
        redisTemplate.delete(keys.stream().filter(s -> !s.contains(properties.getOnlineKey())).filter(s -> !s.contains(properties.getCodeKey())).collect(Collectors.toList()));
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
    public void saveCode(String key, Object val) {
        redisTemplate.opsForValue().set(key,val);
        redisTemplate.expire(key,expiration, TimeUnit.MINUTES);
    }

    @Override
    public void download(List<RedisVo> redisVos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RedisVo redisVo : redisVos) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("key", redisVo.getKey());
            map.put("value", redisVo.getValue());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
