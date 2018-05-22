package org.pine.ibaits.datasource;

public interface IDataSource {
	String getDatasourceGuid();

	String getDriverclassname();

	String getUrl();

	String getPassword();

	String getUsername();
}
