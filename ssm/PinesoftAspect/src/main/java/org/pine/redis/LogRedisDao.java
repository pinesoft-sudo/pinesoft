package org.pine.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.pine.aspect.LogInfo;
import org.pine.redis.AbstractBaseRedis;
import org.springframework.stereotype.Component;

@Component
public class LogRedisDao extends AbstractBaseRedis<String, LogInfo> implements IRedisDao<LogInfo> {

	@Override
	public String add(LogInfo t) {
		String newUUID = null;
		try {
			newUUID = UUID.randomUUID().toString();
			redisTemplate.opsForValue().set(newUUID, t);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return newUUID;
		 
	}
	
	public String add(LogInfo t,int index) {
		changeRedisDB(index);
		return add(t);
	}

	@Override
	public List<String> add(List<LogInfo> list) {
		List<String> newUUIDList = null;
		try {
			if (list != null && list.size() > 0) {
				newUUIDList = new ArrayList<String>();
				for (LogInfo log : list) {
					newUUIDList.add(add(log));
				}
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return newUUIDList;
	}

	@Override
	public void delete(String key) {
		List<String> list = new ArrayList<String>();
		list.add(key);
		delete(list);
	}

	@Override
	public void delete(List<String> keys) {
		redisTemplate.delete(keys);
	}

	@Override
	public boolean update(LogInfo t) {
		try {
			redisTemplate.opsForValue().set(t.getGuid(), t);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public LogInfo get(String key) {
		return  redisTemplate.opsForValue().get(key);
	}

}
