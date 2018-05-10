package org.pine.redis;

import javax.annotation.Resource;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class AbstractBaseRedis<K, V> {
	@Resource
	protected  RedisTemplate<K, V> redisTemplate;

	public  void changeRedisDB(int index) {
		JedisConnectionFactory factory=(JedisConnectionFactory) redisTemplate.getConnectionFactory();
		if(factory!=null&&factory.getDatabase()!=index) {
			factory.setDatabase(index);	
		}
		redisTemplate.setConnectionFactory(factory);
	}

}