package org.pine.boot.netty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:config/custom.properties") // 引入自己的properties
@Component
public class NettyParam {
	@Value("${wss.server.port}")
	public  int nPort;
	@Value("${wss.server.host}")
	public String nHost;
}
