package org.pine.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author xier:
 * @Description:
 * @date 创建时间：2017年11月23日 下午2:32:32
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	// @Override
	// public void configureMessageConverters(List<HttpMessageConverter<?>>
	// converters) {
	// super.configureMessageConverters(converters);
	//
	// FastJsonHttpMessageConverter fastConverter = new
	// FastJsonHttpMessageConverter();
	//
	// FastJsonConfig fastJsonConfig = new FastJsonConfig();
	// fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
	// fastConverter.setFastJsonConfig(fastJsonConfig);
	//
	// converters.add(fastConverter);
	// }

	@Bean
	public InternalResourceViewResolver defaultViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");

		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
		super.addResourceHandlers(registry);
	}
}