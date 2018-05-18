package org.pine.soft.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.pine.soft.datasource.DynamicDataSource;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class DataSourceConfig {

	public final static String PRIMARY_DATASOURCE_KEY = "druid";
	
	@Bean(name = "dynamicDataSource")
	public DynamicDataSource dataSource(DataSource dataSource) {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(PRIMARY_DATASOURCE_KEY, dataSource);
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		dynamicDataSource.setTargetDataSources(targetDataSources);
		dynamicDataSource.setDefaultTargetDataSource(dataSource);

		return dynamicDataSource;
	}
}
