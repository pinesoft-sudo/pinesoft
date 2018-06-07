package org.pine.mq.rabbit;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

//根据AMQP规范, 生产者其实仅关注Exchange与Route Key, 消费者仅关注Queue
//因为此处在框架内部调用，故暂时生产和消费者配置在合并在一起
@Configuration
public class RabbitConfig {

	public static final String DEFAULT_QUEUE_NAME = "pine_queue";
	public static final String DEFAULT_EXCHANGE_NAME = "pine_exchange";
	public static final String DEFAULT_EXCHANGE_TYPE = "topic";

	@Autowired
	private RabbitProperties rabbitProperties;

	@Bean
	public Queue queue_rabbit() {
		// 是否持久化
		boolean durable = true;
		// 仅创建者可以使用的私有队列，断开后自动删除
		boolean exclusive = false;
		// 当所有消费客户端连接断开后，是否自动删除队列
		boolean autoDelete = false;
		// 队列名称
		String queue = StringUtils.isEmpty(rabbitProperties.getQueueName()) ? DEFAULT_QUEUE_NAME
				: rabbitProperties.getQueueName();
		return new Queue(queue, durable, exclusive, autoDelete);
	}

	@Bean
	public AbstractExchange exchange() {
		// 是否持久化
		boolean durable = true;
		// 当所有消费客户端连接断开后，是否自动删除交换器
		boolean autoDelete = false;
		// 交换器名称
		String exchange = StringUtils.isEmpty(rabbitProperties.getExchangeName()) ? DEFAULT_EXCHANGE_NAME
				: rabbitProperties.getExchangeName();
		// 交换器类型
		// DirectExchange:按照routingkey分发到指定队列 ;
		// TopicExchange:多关键字匹配（比Direct方式多了*和#的通配符）;
		// FanoutExchange:将消息分发到所有的绑定队列（订阅/发布），无routingkey的概念;
		// HeadersExchange ：通过添加属性key-value匹配;
		String exchangeType = StringUtils.isEmpty(rabbitProperties.getExchangeType()) ? DEFAULT_EXCHANGE_TYPE
				: rabbitProperties.getExchangeType();
		switch (exchangeType.toLowerCase()) {
		case "direct":
			return new DirectExchange(exchange, durable, autoDelete);
		case "headers":
			return null;// TODO:延后处理
		case "fanout":
			return new FanoutExchange(exchange, durable, autoDelete);
		case "topic":
		default:
			return new TopicExchange(exchange, durable, autoDelete);
		}
	}

	@Bean
	public Binding binding() {
		// 交换器类型
		String exchangeType = StringUtils.isEmpty(rabbitProperties.getExchangeType()) ? DEFAULT_EXCHANGE_TYPE
				: rabbitProperties.getExchangeType();
		switch (exchangeType.toLowerCase()) {
		case "direct":
			return BindingBuilder.bind(queue_rabbit()).to((DirectExchange) exchange()).with(rabbitProperties.getQueueName());
		case "topic":
			//此处routing_key都用queue-name暂时替代，后面根据实际情况修改
			return BindingBuilder.bind(queue_rabbit()).to((TopicExchange) exchange()).with(rabbitProperties.getQueueName());
		case "headers":
			return null;// TODO:延后处理
		case "fanout":
			return BindingBuilder.bind(queue_rabbit()).to((FanoutExchange) exchange());
		default:
			return BindingBuilder.bind(queue_rabbit()).to((DirectExchange) exchange()).with(rabbitProperties.getQueueName());
		}
	}

/*	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(rabbitProperties.getQueueName());
		container.setMessageListener(listenerAdapter);
		return container;
	}*/

	/*
	 * 可以在初始化的时候直接指定消费者及其方法，此处经作为参考
	 * 
	 * @Bean MessageListenerAdapter listenerAdapter(RabbitReceiver receiver) {
	 * return new MessageListenerAdapter(receiver, "receive"); }
	 */
}
