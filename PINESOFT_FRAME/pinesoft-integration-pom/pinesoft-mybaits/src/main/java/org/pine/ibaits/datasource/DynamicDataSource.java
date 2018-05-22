package org.pine.ibaits.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.pine.ibaits.config.DataSourceConfig;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * DynamicDataSource 动态数据源
 * 
 * @author yangs
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	private static Map<Object, Object> dataSources;

	public static void remove(String guid) {
		if (dataSources.containsKey(guid))
			dataSources.remove(guid);
	}

	private String getDefaultKey() {
		return DataSourceConfig.PRIMARY_DATASOURCE_KEY;
	}

	private String getDynamicKey(String type) {
		return type + "dataSource";
	}

	public DataSource getDefaultDataSource() {
		return (DataSource) dataSources.get(getDefaultKey());
	}

	// 新增动态数据源 ，当数据源更新操作时，需要调用该方法重置
	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		if (dataSources == null)
			dataSources = new HashMap<>();
		dataSources = targetDataSources;
		super.setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		Object lookupKey = DBContextHolder.getKey();
		if (lookupKey == null || lookupKey.getClass().equals(String.class))// 为空或者切换到platform数据时
		{
			if (lookupKey == null)
				lookupKey = getDefaultKey();
			else
				lookupKey = getDynamicKey(lookupKey.toString());
		} else {
			String key = ((IDataSource) lookupKey).getDatasourceGuid();
			if (!dataSources.containsKey(key)) {

				Object dataSource = getDataSource(lookupKey);// 这里还需要做处理
				if (dataSource != null) {
					dataSources.put(key, dataSource);
					setTargetDataSources(dataSources);
					return key;
				} else
					throw new NullPointerException("DynamicDataSource:找不到key：" + lookupKey);
			} else
				return key;
		}
		return lookupKey;
	}

	private Object getDataSource(Object lookupKey) {

		IDataSource dataSource = (IDataSource) lookupKey;
		return createDataSource(dataSource.getDriverclassname(), dataSource.getUrl(), dataSource.getUsername(),
				dataSource.getPassword());
	}

	private DruidDataSource createDataSource(String driverClassName, String url, String username, String password) {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setTestWhileIdle(false);
		dataSource.setMaxActive(20);
		dataSource.setMaxWait(10000);
		return dataSource;
	}
}