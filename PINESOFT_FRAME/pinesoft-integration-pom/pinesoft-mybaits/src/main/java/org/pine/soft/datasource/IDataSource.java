package org.pine.soft.datasource;

public interface IDataSource {
	String getDatasourceGuid();

	String getDriverclassname();

	String getUrl();

	String getPassword();

	String getUsername();
}
