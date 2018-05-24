package org.pine.mq.active;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.util.StringUtils;

@SuppressWarnings("unused")
@Configuration
public class MQConfig {

	@Autowired
	private MQProperties mQProperties;

	public static final String DEFAULT_QUEUE_NAME = "pinesoft-queue";
	public static final String DEFAULT_TOPIC_NAME = "pinesoft-topic";
	public static final String QUEUE_LISTENER_CONTAINER_FACTORY = "jmsListenerContainerQueue";
	public static final String TOPIC_LISTENER_CONTAINER_FACTORY = "jmsListenerContainerTopic";

	@Bean
	public Queue queue() {
		String queueName = mQProperties.getQueueName();
		if (StringUtils.isEmpty(queueName))
			queueName = DEFAULT_QUEUE_NAME;
		return new ActiveMQQueue(queueName);
	}

	@Bean
	public Topic topic() {
		String topicName = mQProperties.getTopicName();
		if (StringUtils.isEmpty(topicName))
			topicName = DEFAULT_TOPIC_NAME;
		return new ActiveMQTopic(topicName);
	}

	@Bean
	public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ConnectionFactory activeMQConnectionFactory) {
		DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
		// 设置为发布订阅方式, 默认情况下使用的生产消费者方式
		bean.setPubSubDomain(true);
		bean.setConnectionFactory(activeMQConnectionFactory);
		return bean;
	}

	@Bean
	public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ConnectionFactory activeMQConnectionFactory) {
		DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
		bean.setConnectionFactory(activeMQConnectionFactory);
		return bean;
	}

	@Bean
	//该注解指如果spring.activemq.pool.enabled=false或者该配置属性不存在会正常加载该方法到bean
	//由于springboot已经集成ActiveMQ相关组件和配置（在application.properties），故在开启或不开启线程池，该bean都可以不用注入
	@ConditionalOnProperty(prefix = "spring.activemq.pool", name = "enabled", havingValue = "false", matchIfMissing = true)
	public ActiveMQConnectionFactory jmsConnectionFactory() {
		return new MQConnectionFactories(mQProperties)
				.createConnectionFactory(ActiveMQConnectionFactory.class);
	}

}
