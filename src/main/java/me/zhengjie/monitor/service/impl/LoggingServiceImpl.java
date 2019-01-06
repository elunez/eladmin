package me.zhengjie.monitor.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import me.zhengjie.common.utils.IpUtil;
import me.zhengjie.common.utils.RequestHolder;
import me.zhengjie.core.security.AuthorizationUser;
import me.zhengjie.core.utils.JwtTokenUtil;
import me.zhengjie.monitor.domain.Logging;
import me.zhengjie.monitor.repository.LoggingRepository;
import me.zhengjie.monitor.service.LoggingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author jie
 * @date 2018-11-24
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LoggingServiceImpl implements LoggingService {

    @Autowired
    private LoggingRepository loggingRepository;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final String LOGINPATH = "authenticationLogin";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ProceedingJoinPoint joinPoint, Logging logging){

        // 获取request
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        me.zhengjie.common.aop.log.Log log = method.getAnnotation(me.zhengjie.common.aop.log.Log.class);

        // 描述
        if (log != null) {
            logging.setDescription(log.description());
        }

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";

        String params = "{";
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        // 用户名
        String username = "";

        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params += " " + argNames[i] + ": " + argValues[i];
            }
        }

        // 获取IP地址
        logging.setRequestIp(IpUtil.getIP(request));

        if(!LOGINPATH.equals(signature.getName())){
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            username = userDetails.getUsername();
        } else {
            AuthorizationUser user = JSONUtil.toBean(new JSONObject(argValues[0]),AuthorizationUser.class);
            username = user.getUsername();

        }
        logging.setMethod(methodName);
        logging.setUsername(username);
        logging.setParams(params + " }");
        loggingRepository.save(logging);
    }
}
