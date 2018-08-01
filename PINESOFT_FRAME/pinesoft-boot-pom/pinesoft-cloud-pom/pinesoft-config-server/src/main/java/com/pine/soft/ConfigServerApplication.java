package com.pine.soft;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

//注解可以包括下面三个注解@Configuration@EnableAutoConfiguration@ComponentScan，但默认需要将该文件置于类的顶级位置
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(ConfigServerApplication.class, args);
		System.out.println("远程配置中心已经开启");
	}
}
