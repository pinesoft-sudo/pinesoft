package org.pine.mq.test;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pine.mq.DemoApplication;
import org.pine.mq.active.MQProducer;
import org.pine.mq.rabbit.RabbitProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class TestMQ {
	@Autowired
	private MQProducer mqProducer;
	@Autowired
	private RabbitProducer rabbitProducer;

	@Test
	public void queueTest() throws InterruptedException, IOException {
		for (int i = 0; i < 10; i++) {
			mqProducer.sendQueueMessage("gisquest mq queue test"+i);
			Thread.sleep(2000);
		}
	}

	@Test
	public void topicTest() throws InterruptedException, IOException {
		for (int i = 0; i < 10; i++) {
			mqProducer.sendTopicMessage("gisquest mq topic test"+i);
			Thread.sleep(2000);
		}
	}
	
	//发布订阅目前只能通过queue来实现，再研究
	@Test
	public void rabbitTest() throws InterruptedException, IOException {
		for (int i = 0; i < 10; i++) {
			rabbitProducer.sendMessage("gisquest rabbit test"+i);
			Thread.sleep(2000);
		}
	}
}
