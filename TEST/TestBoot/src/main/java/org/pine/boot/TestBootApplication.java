package org.pine.boot;

import java.io.IOException;

import org.pine.boot.netty.NettyParam;
import org.pine.common.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;

//开启数据事务（引入它们依赖的时候，已经默认对jpa、jdbc、mybatis开启了事事务，其他orm还是需要添加）
//@EnableTransactionManagement

//注解可以包括下面三个注解@Configuration@EnableAutoConfiguration@ComponentScan，但默认需要将该文件置于类的顶级位置
@SpringBootApplication
@EnableCaching // 开启缓存技术（ConcurrentMapCacheManager ）
@EnableScheduling // 开启任务调度
// @ImportResource("classpath*:config/redisContext.xml")
@ComponentScan(basePackages = "org.pine") // 添加aop切面的扫描注册（对PinesoftAspect里的org.pine.aspect包），否则引用外部项目aop会失效，多个扫描包用“,”分割
public class TestBootApplication {

	@Autowired
	public NettyParam np;
	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(TestBootApplication.class, args);
		LogUtil.info("****spring boot is start!****");
	}

	// 通过RestTemplate消费服务，注册一个RestTemplate bean
	// 使用RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	// 注册netty-socketio服务，可通过参数进行身份验证等
	@Bean
	public SocketIOServer socketIOServer() {
		Configuration config = new Configuration();
		config.setHostname(np.nHost);
		config.setPort(np.nPort);

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
