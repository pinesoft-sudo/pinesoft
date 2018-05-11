package org.pine.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pine.boot.entity.RecordFile;
import org.pine.boot.rabbit.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alibaba.fastjson.JSON;

//如果test的包名称和项目的包同名，SpringBootTest可省略application指向
//如果test的包名称和项目的包不同名，SpringBootTest要添加application指向 [@SpringBootTest(classes = TestBootApplication.class)]
@SpringBootTest
@RunWith(SpringRunner.class)
// 用来测试controller
@AutoConfigureMockMvc
public class JUnitTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void insertTest() throws Exception {
		RecordFile param = new RecordFile();
		param.setGuid("123eee");
		param.setFilename("testcc");
		param.setFilesize(99);
		param.setModified("path");
		String jsonstr = JSON.toJSONString(param);
		System.out.println("请求入参：" + jsonstr);

		RequestBuilder request = MockMvcRequestBuilders.post("/rtmp/recordFile/add").param("record", jsonstr);
		MvcResult mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();

		System.out.println(status + "---" + content);
	}

	@Test
	public void updateTest() throws Exception {
		RecordFile param = new RecordFile();
		param.setGuid("123eee");
		param.setFilesize(1199);
		param.setModified("path-update");
		String jsonstr = JSON.toJSONString(param);
		System.out.println("请求入参：" + jsonstr);

		RequestBuilder request = MockMvcRequestBuilders.post("/rtmp/recordFile/update").param("record", jsonstr);
		MvcResult mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();

		System.out.println(status + "---" + content);
	}

	@Autowired
	private RabbitSender sender;
	@Test
	public void testSend() throws Exception {
		sender.send();
	}
}
