package org.pine.soft.datasource;

/**
 * 线程安全的ThreadLocal 切换数据源
 * 
 * @author yangs
 *
 */
public class DBContextHolder {
	 //存储datasourceguid 用于切换数据源
	private static final ThreadLocal<Object> contextHolder = new ThreadLocal<>();

	public static void setKey(Object key) {
		contextHolder.set(key);
	}

	public static void setKey() {
		contextHolder.set(null);
	}

	public static Object getKey() {
		Object key = contextHolder.get();
		if (key != null && key.toString().isEmpty())
			key = null;
		return key;
	}

	public static void clear() {
		contextHolder.remove();
	}
}
