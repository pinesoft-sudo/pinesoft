package org.pine.redis;

import java.util.List;

public interface IRedisDao<T> {

    String add(T t);  
      
    List<String> add(List<T> list);  
      
    void delete(String key);  

    void delete(List<String> keys);  
      
    boolean update(T t);  

    T get(String keyId);  
}
