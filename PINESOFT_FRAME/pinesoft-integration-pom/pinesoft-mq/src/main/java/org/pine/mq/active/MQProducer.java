package org.pine.mq.active;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MQProducer {
	@Resource(name = "jmsMessagingTemplate")
	private JmsMessagingTemplate jmsTemplate;
	@Autowired
	private Queue queue_active;
	@Autowired
	private Topic topic;

	public void sendMessage(Destination destination, final Object message) {
		jmsTemplate.convertAndSend(destination, message);
	}

	//队列发送方式
	public void sendQueueMessage(final Object message) {
		jmsTemplate.convertAndSend(queue_active, message);
	}

	// 发布/订阅方式
	public void sendTopicMessage(final Object message) {
		jmsTemplate.convertAndSend(topic, message);
	}
}
