package com.pine.soft;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//注解可以包括下面三个注解@Configuration@EnableAutoConfiguration@ComponentScan，但默认需要将该文件置于类的顶级位置
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(EurekaServerApplication.class, args);
		System.out.println("Eureka 注册中心已经开启");
	}

}
