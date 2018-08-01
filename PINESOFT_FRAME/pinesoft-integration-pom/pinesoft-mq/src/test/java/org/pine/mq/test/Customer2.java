package org.pine.mq.test;

import org.pine.mq.active.MQConfig;
import org.pine.mq.rabbit.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Customer2 {
	@JmsListener(destination = MQConfig.DEFAULT_TOPIC_NAME)
	public void receiveTopic(String text) {
		System.out.println("customer-topic2收到的报文为:" + text);
	}
	
	@JmsListener(destination = MQConfig.DEFAULT_QUEUE_NAME, containerFactory = MQConfig.QUEUE_LISTENER_CONTAINER_FACTORY)
	// @SendTo("out.queue") 可以再转发另一个队列
	public void receiveQueue(String text) {
		System.out.println("customer-queue2收到的报文为:" + text);
	}
	
	@RabbitListener(queues = {RabbitConfig.DEFAULT_QUEUE_NAME})
	public void processBootTask(String text) {
		System.out.println("customer-rabbit2收到的报文为:" + text);
	}
}
