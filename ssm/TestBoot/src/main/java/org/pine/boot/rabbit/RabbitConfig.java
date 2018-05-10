package org.pine.boot.rabbit;

import org.pine.common.util.LogUtil;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig extends RabbitParam {
	@Bean
	public Queue queue() {
		// 是否持久化
		boolean durable = true;
		// 仅创建者可以使用的私有队列，断开后自动删除
		boolean exclusive = false;
		// 当所有消费客户端连接断开后，是否自动删除队列
		boolean autoDelete = false;
		return new Queue(queue, durable, exclusive, autoDelete);
	}

	@Bean
	public AbstractExchange exchange() {
		// 是否持久化
		boolean durable = true;
		// 当所有消费客户端连接断开后，是否自动删除路由
		boolean autoDelete = false;

		switch (e_type.toLowerCase()) {
		case "direct":
			return new DirectExchange(exchange, durable, autoDelete);
		case "topic":
			return new TopicExchange(exchange, durable, autoDelete);
		case "headers":
			return new HeadersExchange(exchange, durable, autoDelete);
		case "fanout":
			return new FanoutExchange(exchange, durable, autoDelete);
		default:
			return new TopicExchange(exchange, durable, autoDelete);
		}
	}
	
	 @Bean
	    public Binding binding(Queue queue, TopicExchange exchange) {
		 
		   LogUtil.info(exchange().getType());
		 
	        return BindingBuilder.bind(queue).to(exchange).with(super.queue);
	    }
	 
	 @Bean
	    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
	            MessageListenerAdapter listenerAdapter) {
	        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	        container.setConnectionFactory(connectionFactory);
	        container.setQueueNames(super.queue);
	        container.setMessageListener(listenerAdapter);
	        return container;
	    }
	 
	    @Bean
	    MessageListenerAdapter listenerAdapter(RabbitReceiver receiver) {
	        return new MessageListenerAdapter(receiver, "receive");
	    }
}
