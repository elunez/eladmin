package me.zhengjie.modules.quartz.utils;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.utils.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;

/**
 * 执行定时任务
 * @author
 */
@Slf4j
public class QuartzRunnable implements Runnable {

	private Object target;
	private Method method;
	private String params;

	QuartzRunnable(String beanName, String methodName, String params)
			throws NoSuchMethodException, SecurityException {
		this.target = SpringContextHolder.getBean(beanName);
		this.params = params;

		if (StringUtils.isNotBlank(params)) {
			this.method = target.getClass().getDeclaredMethod(methodName, String.class);
		} else {
			this.method = target.getClass().getDeclaredMethod(methodName);
		}
	}

	@Override
	public void run() {
		try {
			ReflectionUtils.makeAccessible(method);
			if (StringUtils.isNotBlank(params)) {
				method.invoke(target, params);
			} else {
				method.invoke(target);
			}
		} catch (Exception e) {
			log.error("定时任务执行失败",e);
		}
	}

}
