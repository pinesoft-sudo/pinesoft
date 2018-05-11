package org.pine.boot.rabbit;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitSender extends RabbitParam {
	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send() {
		String context = "Hello " + new Date();
		System.out.println("Sender : " + context);
		this.rabbitTemplate.convertAndSend(queue, context);
		
	}

}
