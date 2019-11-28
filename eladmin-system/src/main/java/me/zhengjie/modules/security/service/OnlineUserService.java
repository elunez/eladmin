package me.zhengjie.modules.security.service;

import me.zhengjie.modules.security.config.SecurityProperties;
import me.zhengjie.modules.security.security.vo.JwtUser;
import me.zhengjie.modules.security.security.vo.OnlineUser;
import me.zhengjie.utils.EncryptUtils;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.StringUtils;
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

    private final SecurityProperties properties;
    private final RedisTemplate redisTemplate;

    public OnlineUserService(SecurityProperties properties, RedisTemplate redisTemplate) {
        this.properties = properties;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 保存在线用户信息
     * @param jwtUser
     * @param token
     * @param request
     */
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
        redisTemplate.opsForValue().set(properties.getOnlineKey() + token, onlineUser);
        redisTemplate.expire(properties.getOnlineKey() + token,properties.getTokenValidityInSeconds(), TimeUnit.MILLISECONDS);
    }

    /**
     * 查询全部数据
     * @param filter
     * @param pageable
     * @return
     */
    public Page<OnlineUser> getAll(String filter, Pageable pageable){
        List<OnlineUser> onlineUsers = getAll(filter);
        return new PageImpl<OnlineUser>(
                PageUtil.toPage(pageable.getPageNumber(),pageable.getPageSize(),onlineUsers),
                pageable,
                onlineUsers.size());
    }

    /**
     * 查询全部数据，不分页
     * @param filter
     * @return
     */
    public List<OnlineUser> getAll(String filter){
        List<String> keys = new ArrayList<>(redisTemplate.keys(properties.getOnlineKey() + "*"));
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

    /**
     * 踢出用户
     * @param val
     * @throws Exception
     */
    public void kickOut(String val) throws Exception {
        String key = properties.getOnlineKey() + EncryptUtils.desDecrypt(val);
        redisTemplate.delete(key);
    }

    /**
     * 退出登录
     * @param token
     */
    public void logout(String token) {
        String key = properties.getOnlineKey() + token;
        redisTemplate.delete(key);
    }

    /**
     * 导出
     * @param all
     * @param response
     * @throws IOException
     */
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

    /**
     * 查询用户
     * @param key
     * @return
     */
    public OnlineUser getOne(String key) {
        return (OnlineUser)redisTemplate.opsForValue().get(key);
    }
}
