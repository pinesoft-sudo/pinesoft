package org.pine.boot.rabbit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:config/custom.properties") // 引入自己的properties
public class RabbitParam {
	@Value("${pine.mq.queue}")
	public  String queue;
	
	@Value("${pine.mq.exchange}")
	public String exchange;
	@Value("${pine.mq.exchange.type}")
	public String e_type;

}
