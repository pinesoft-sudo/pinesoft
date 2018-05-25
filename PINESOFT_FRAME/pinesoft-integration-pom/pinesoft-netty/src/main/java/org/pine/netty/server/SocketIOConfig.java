package org.pine.netty.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;

/**
 * netty-socketio服务初始化及注入
 * 
 * @author yans
 *
 */
@Configuration
public class SocketIOConfig {

	@Value("${socketio.server.port}")
	public int nPort;
	@Value("${socketio.server.host}")
	public String nHost;

	// 注册netty-socketio服务，可通过参数进行身份验证等
	@Bean
	public SocketIOServer socketIOServer() {
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		config.setHostname(nHost);
		config.setPort(nPort);

		config.setAuthorizationListener(new AuthorizationListener() {
			@Override
			public boolean isAuthorized(HandshakeData data) {
				// http://localhost:8081?username=test&password=test
				// 例如果使用上面的链接进行connect，可以使用如下代码获取用户密码信息
				// String username = data.getSingleUrlParam("username");
				// String password = data.getSingleUrlParam("password");
				return true;
			}
		});

		final SocketIOServer server = new SocketIOServer(config);
		return server;
	}

	// 完成对SocketIOServer bean实例化做前后置操作，然后后注入Spring IoC容器 [继承BeanPostProcessor]
	@Bean
	public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
		return new SpringAnnotationScanner(socketServer);
	}

}
