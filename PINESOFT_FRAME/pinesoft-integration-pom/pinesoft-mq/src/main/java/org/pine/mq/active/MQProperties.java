package org.pine.mq.active;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "spring.activemq")
public class MQProperties {

	private String brokerUrl;

	private boolean inMemory = true;

	private String user;

	private String password;

	private Pool pool = new Pool();

	private Packages packages = new Packages();

	private String queueName;

	private String topicName;

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getBrokerUrl() {
		return this.brokerUrl;
	}

	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}

	public boolean isInMemory() {
		return this.inMemory;
	}

	public void setInMemory(boolean inMemory) {
		this.inMemory = inMemory;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Pool getPool() {
		return this.pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	public Packages getPackages() {
		return this.packages;
	}

	public  class Pool {

		private boolean enabled;

		private int maxConnections = 1;

		private int idleTimeout = 30000;

		private long expiryTimeout = 0;

		public boolean isEnabled() {
			return this.enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public int getMaxConnections() {
			return this.maxConnections;
		}

		public void setMaxConnections(int maxConnections) {
			this.maxConnections = maxConnections;
		}

		public int getIdleTimeout() {
			return this.idleTimeout;
		}

		public void setIdleTimeout(int idleTimeout) {
			this.idleTimeout = idleTimeout;
		}

		public long getExpiryTimeout() {
			return this.expiryTimeout;
		}

		public void setExpiryTimeout(long expiryTimeout) {
			this.expiryTimeout = expiryTimeout;
		}

	}

	public  class Packages {

		private Boolean trustAll = true;

		private List<String> trusted = new ArrayList<>();

		public Boolean getTrustAll() {
			return this.trustAll;
		}

		public void setTrustAll(Boolean trustAll) {
			this.trustAll = trustAll;
		}

		public List<String> getTrusted() {
			return this.trusted;
		}

		public void setTrusted(List<String> trusted) {
			this.trusted = trusted;
		}

	}


}
