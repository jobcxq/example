package com.example.cnxqin.service.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/24 00:15
 */
@Service
public class RedisService {

    private static final String LOCK_VALUE = "1";

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean set(String key, String value, long expireTime){
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
        return true;
    }

    /**
     * 设置
     * @param key
     * @param value
     * @param expireTime 过期时间，单位：秒
     */
    public boolean set(String key, Object value, long expireTime){
        return set(key, JSONObject.toJSONString(value), expireTime);
    }

    public boolean setIfAbsent(String key, String value, long expireTime){
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(result);
    }

    public boolean setIfAbsent(String key, Object value, long expireTime){
        return setIfAbsent(key, JSONObject.toJSONString(value), expireTime);
    }

    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public <T> T get(String key, Class<T> clazz){
        String value = redisTemplate.opsForValue().get(key);

        if(Objects.isNull(value)){
            return (T) value;
        }

        if(clazz == String.class){
            return (T) value;
        }
        return JSONObject.parseObject(value, clazz);
    }

    public boolean tryLock(String key, long expireTime){
        return setIfAbsent(key, LOCK_VALUE, expireTime);
    }

    public boolean tryLock(String key, String value, long expireTime){
        return setIfAbsent(key, value, expireTime);
    }

    public boolean unLock(String key){
        return redisTemplate.delete(key);
    }

    /**
     * 仅能删除指定 value 值的锁
     * @param key
     * @param value
     * @return
     */
    public boolean unLock(String key, String value){
        String result = this.get(key);
        if(Objects.nonNull(result)){
            return true;
        }
        if(!result.equals(value)){
            return false;
        }
        return redisTemplate.delete(key);
    }


}
