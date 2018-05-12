package org.pine.common.util;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

@SuppressWarnings("unchecked")
public class ApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getContext() {
		checkApplicationContext();
		return applicationContext;
	}
	
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		Map<String, T> object = applicationContext.getBeansOfType(clazz);

		if (object.values().size() > 0)
			return object.values().iterator().next();
		return null;
	}

	private static void checkApplicationContext() {
		Assert.notNull(applicationContext, "applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
	}

}
