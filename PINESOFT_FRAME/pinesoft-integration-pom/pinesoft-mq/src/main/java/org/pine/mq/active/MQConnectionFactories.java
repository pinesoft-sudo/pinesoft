package org.pine.mq.active;
import java.lang.reflect.InvocationTargetException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.pine.mq.active.MQProperties.Packages;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class MQConnectionFactories {
	private static final String DEFAULT_EMBEDDED_BROKER_URL = "vm://localhost?broker.persistent=false";

	private static final String DEFAULT_NETWORK_BROKER_URL = "tcp://localhost:61616";

	private final MQProperties properties;

	MQConnectionFactories(MQProperties properties) {
		Assert.notNull(properties, "Properties must not be null");
		this.properties = properties;
	}

	public <T extends ActiveMQConnectionFactory> T createConnectionFactory(Class<T> factoryClass) {
		try {
			return doCreateConnectionFactory(factoryClass);
		} catch (Exception ex) {
			throw new IllegalStateException("Unable to create " + "ActiveMQConnectionFactory", ex);
		}
	}

	private <T extends ActiveMQConnectionFactory> T doCreateConnectionFactory(Class<T> factoryClass) throws Exception {
		T factory = createConnectionFactoryInstance(factoryClass);
		Packages packages = this.properties.getPackages();
		if (packages.getTrustAll() != null) {
			factory.setTrustAllPackages(packages.getTrustAll());
		}
		if (!packages.getTrusted().isEmpty()) {
			factory.setTrustedPackages(packages.getTrusted());
		}
		return factory;
	}

	private <T extends ActiveMQConnectionFactory> T createConnectionFactoryInstance(Class<T> factoryClass)
			throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String brokerUrl = determineBrokerUrl();
		String user = this.properties.getUser();
		String password = this.properties.getPassword();
		if (StringUtils.hasLength(user) && StringUtils.hasLength(password)) {
			return factoryClass.getConstructor(String.class, String.class, String.class).newInstance(user, password,
					brokerUrl);
		}
		return factoryClass.getConstructor(String.class).newInstance(brokerUrl);
	}

	String determineBrokerUrl() {
		if (this.properties.getBrokerUrl() != null) {
			return this.properties.getBrokerUrl();
		}
		if (this.properties.isInMemory()) {
			return DEFAULT_EMBEDDED_BROKER_URL;
		}
		return DEFAULT_NETWORK_BROKER_URL;
	}
}
