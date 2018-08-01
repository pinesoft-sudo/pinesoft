package com.pine.soft.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pine.soft.hystrix.TestRemoteHystrix;

//fegin方式客户端调用服务，并开启熔断器
@FeignClient(name= "spring-cloud-producer",fallback = TestRemoteHystrix.class)
public interface TestRemote {
	@RequestMapping(value = "/test")
    public String test(@RequestParam(value = "name") String name);
}
