package me.zhengjie.modules.security.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSON;

import me.zhengjie.config.ResponseWrapper;

public class PromethuseResponseFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse)response);//转换成代理类
        filterChain.doFilter(request, wrapperResponse);

        byte[] content = wrapperResponse.getContent();//获取返回值
        if (content.length > 0) {
            String jsonString = new String(content);
            Object plaObject = JSON.parse(jsonString);
            //把返回值输出到客户端
            ServletOutputStream out = response.getOutputStream();
            // Prometheus actuator endpoint should produce a text/plain response
            // https://github.com/spring-projects/spring-boot/issues/28446
            response.setContentType("text/plain");
            out.write(plaObject.toString().getBytes());
            out.flush();
            out.close();
        }

    }
}
