package me.zhengjie.common.aop.log;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.common.exception.BadRequestException;
import me.zhengjie.common.utils.ThrowableUtil;
import me.zhengjie.monitor.domain.Logging;
import me.zhengjie.monitor.service.LoggingService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author jie
 * @date 2018-11-24
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    private LoggingService loggingService;

    private long currentTime = 0L;

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(me.zhengjie.common.aop.log.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint){
        Object result = null;
        currentTime = System.currentTimeMillis();
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new BadRequestException(e.getMessage());
        }
        Logging logging = new Logging("INFO",System.currentTimeMillis() - currentTime);
        loggingService.save(joinPoint, logging);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Logging logging = new Logging("ERROR",System.currentTimeMillis() - currentTime);
        logging.setExceptionDetail(ThrowableUtil.getStackTrace(e));
        loggingService.save((ProceedingJoinPoint)joinPoint, logging);
    }
}
