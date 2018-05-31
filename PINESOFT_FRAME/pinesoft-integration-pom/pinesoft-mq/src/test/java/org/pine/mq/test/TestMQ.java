package org.pine.mq.test;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pine.mq.DemoApplication;
import org.pine.mq.active.MQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class TestMQ {
	@Autowired
	private MQProducer mQProducer;

	@Test
	public void queueTest() throws InterruptedException, IOException {
		for (int i = 0; i < 10; i++) {
			mQProducer.sendQueueMessage("gisquest mq queue test"+i);
			Thread.sleep(2000);
		}
	}

	@Test
	public void topicTest() throws InterruptedException, IOException {
		for (int i = 0; i < 10; i++) {
			mQProducer.sendTopicMessage("gisquest mq topic test"+i);
			Thread.sleep(2000);
		}

	}
}
