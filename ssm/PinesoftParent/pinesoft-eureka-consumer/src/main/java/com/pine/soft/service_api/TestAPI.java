package com.pine.soft.service_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pine.soft.feign.TestRemote;

@RestController
public class TestAPI {

	@Autowired
    TestRemote HelloRemote;
	
    @RequestMapping("/test/{name}")
    public String test(@PathVariable("name") String name) {
        return HelloRemote.test(name);
    }
}
