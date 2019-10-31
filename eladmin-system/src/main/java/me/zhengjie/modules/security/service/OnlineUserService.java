package me.zhengjie.modules.security.service;

import me.zhengjie.modules.security.security.JwtUser;
import me.zhengjie.modules.security.security.OnlineUser;
import me.zhengjie.utils.EncryptUtils;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Zheng Jie
 * @Date 2019年10月26日21:56:27
 */
@Service
@SuppressWarnings({"unchecked","all"})
public class OnlineUserService {

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.online}")
    private String onlineKey;

    private final RedisTemplate redisTemplate;

    public OnlineUserService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(JwtUser jwtUser, String token, HttpServletRequest request){
        String job = jwtUser.getDept() + "/" + jwtUser.getJob();
        String ip = StringUtils.getIp(request);
        String browser = StringUtils.getBrowser(request);
        String address = StringUtils.getCityInfo(ip);
        OnlineUser onlineUser = null;
        try {
            onlineUser = new OnlineUser(jwtUser.getUsername(), job, browser , ip, address, EncryptUtils.desEncrypt(token), new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisTemplate.opsForValue().set(onlineKey + token, onlineUser);
        redisTemplate.expire(onlineKey + token,expiration, TimeUnit.MILLISECONDS);
    }

    public Page<OnlineUser> getAll(String filter, Pageable pageable){
        List<OnlineUser> onlineUsers = getAll(filter);
        return new PageImpl<OnlineUser>(
                PageUtil.toPage(pageable.getPageNumber(),pageable.getPageSize(),onlineUsers),
                pageable,
                onlineUsers.size());
    }

    public List<OnlineUser> getAll(String filter){
        List<String> keys = new ArrayList<>(redisTemplate.keys(onlineKey + "*"));
        Collections.reverse(keys);
        List<OnlineUser> onlineUsers = new ArrayList<>();
        for (String key : keys) {
            OnlineUser onlineUser = (OnlineUser) redisTemplate.opsForValue().get(key);
            if(StringUtils.isNotBlank(filter)){
                if(onlineUser.toString().contains(filter)){
                    onlineUsers.add(onlineUser);
                }
            } else {
                onlineUsers.add(onlineUser);
            }
        }
        Collections.sort(onlineUsers, (o1, o2) -> {
            return o2.getLoginTime().compareTo(o1.getLoginTime());
        });
        return onlineUsers;
    }

    public void kickOut(String val) throws Exception {
        String key = onlineKey + EncryptUtils.desDecrypt(val);
        redisTemplate.delete(key);
    }

    public void logout(String token) {
        String key = onlineKey + token;
        redisTemplate.delete(key);
    }

    public void download(List<OnlineUser> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OnlineUser user : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户名", user.getUserName());
            map.put("岗位", user.getJob());
            map.put("登录IP", user.getIp());
            map.put("登录地点", user.getAddress());
            map.put("浏览器", user.getBrowser());
            map.put("登录日期", user.getLoginTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
