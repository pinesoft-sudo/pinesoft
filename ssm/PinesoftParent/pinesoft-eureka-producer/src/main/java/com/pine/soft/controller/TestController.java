package com.pine.soft.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	//可替换后分别运行，用来测试ribbon负载均衡
	@RequestMapping("/test")
    public String test(@RequestParam String name) {
        return "hello "+name+"，this is first messge";
    }
	
   /* @RequestMapping("/test")
    public String test(@RequestParam String name) {
        return "hello "+name+"，this is second messge";
    }*/
}
