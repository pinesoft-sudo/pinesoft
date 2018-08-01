package org.pine.mq.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RabbitProducer {

	/*
	 * 两个Template都可以使用，后者指向更明确
	 * 
	 * @Autowired private AmqpTemplate rabbitTemplate;
	 */
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private RabbitProperties rabbitProperties;

	public void sendMessage(final Object message) {
		//routing_key在此机制下用queue-name代替
		String queue = StringUtils.isEmpty(rabbitProperties.getQueueName()) ? RabbitConfig.DEFAULT_QUEUE_NAME
				: rabbitProperties.getQueueName();
		//交换器名称
		String exchange = StringUtils.isEmpty(rabbitProperties.getExchangeName()) ? RabbitConfig.DEFAULT_EXCHANGE_NAME
				: rabbitProperties.getExchangeName();

		this.rabbitTemplate.convertAndSend(exchange, queue, message);
	}

}
