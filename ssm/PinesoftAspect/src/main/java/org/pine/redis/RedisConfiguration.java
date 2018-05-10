package org.pine.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource("classpath:properties/redis.properties")
public class RedisConfiguration {

	@Value("${redis.pool.maxIdle}")
	private int maxIdle;
	@Value("${redis.pool.maxTotal}")
	private int maxTotal;
	@Value("${redis.pool.testOnBorrow}")
	private boolean testOnBorrow;
	@Value("${redis.pool.maxWaitMillis}")
	private int maxWaitMillis;

	@Value("${redis.host}")
	private String host;
	@Value("${redis.port}")
	private int port;
	@Value("${redis.pass}")
	private String pass;
	@Value("${redis.dbIndex}")
	private int dbIndex;
	@Value("${redis.usePool}")
	private boolean usePool;
	@Value("${redis.timeOut}")
	private int timeOut;

	// 另一种方式是不用value直接用Envirment变量直接getProperty('key')
	// @Autowired
	// private Environment env;
	// env.getProperty(key);

	// 要使用@Value 用${}占位符注入属性，这个bean是必须的,
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setMaxTotal(maxTotal);
		config.setTestOnBorrow(testOnBorrow);
		config.setMaxWaitMillis(maxWaitMillis);
		return config;
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(host);
		// factory.setPassword(pass);
		factory.setPort(port);
		factory.setDatabase(dbIndex);
		factory.setUsePool(usePool);
		factory.setTimeout(timeOut);
		factory.setPoolConfig(jedisPoolConfig());
		factory.afterPropertiesSet();
		return factory;
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate() {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public CacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

		// Number of seconds before expiration. Defaults to unlimited (0)
		cacheManager.setDefaultExpiration(3000); // Sets the default expire time (in seconds)
		return cacheManager;
	}

}
