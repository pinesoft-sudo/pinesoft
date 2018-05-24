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
	private Queue queue;
	@Autowired
	private Topic topic;

	public void sendMessage(Destination destination, final Object message) {
		jmsTemplate.convertAndSend(destination, message);
	}

	public void sendQueueMessage(final Object message) {
		jmsTemplate.convertAndSend(queue, message);
	}

	public void sendTopicMessage(final Object message) {
		jmsTemplate.convertAndSend(topic, message);
	}
}
