package org.pine.common.util;

import javax.annotation.Resource;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationUtils {
	@Resource
	private Environment environment;
	public String GetConfiguration(String name){
		return environment.getProperty(name);
	}
}
