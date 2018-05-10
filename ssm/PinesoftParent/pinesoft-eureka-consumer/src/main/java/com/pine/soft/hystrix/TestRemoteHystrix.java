package com.pine.soft.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.pine.soft.feign.TestRemote;

@Component
public class TestRemoteHystrix implements TestRemote {

	@Override
    public String test(@RequestParam(value = "name") String name) {
        return "hello" +name+", this messge is in hystrix ";
    }

}
