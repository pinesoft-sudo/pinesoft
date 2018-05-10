package org.pine.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/*自定义配置参数对象（注入bean）*/
@Component // 加入到ioc容器
@ConfigurationProperties(prefix = "pine") // 指定配置文件中键名称的前缀,可以不用添加
@PropertySource("classpath:config/custom.properties") // 引入自己的properties
public class CustomConfig {
	private String filepath;
	private String liveuser;

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getLiveuser() {
		return liveuser;
	}

	public void setLiveuser(String liveuser) {
		this.liveuser = liveuser;
	}
}
