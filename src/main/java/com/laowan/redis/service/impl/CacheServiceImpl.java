package com.laowan.redis.service.impl;

import com.laowan.redis.service.CacheCallBack;
import com.laowan.redis.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @program: redis
 * @description: 缓存接口实现类
 * @author: wanli
 * @create: 2020-05-12 09:50
 **/
@Slf4j
@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public boolean setCache(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean setCacheExpire(String key, Object value, long timeout, TimeUnit timeUnit) {
        try {
            if(timeout>0){
                redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
            }else{
                this.setCache(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public <T> T getCache(String key, Class<T> clazz) {
        T o = null;
        try {
            if(key!=null){
                Object result = redisTemplate.opsForValue().get(key);
                 o = result!=null?(T)result:null;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return  o;
    }


    @Override
    public <T> T getCache(String key, Class<T> clazz, CacheCallBack<T, String> callBack) {
        T o = null;
        try {
             o = this.getCache(key,clazz);
             if(o==null){
                 log.info("未命中缓存，执行CacheCallBack回调函数");
                 o = callBack.execute(key);
                 if(o!=null){
                     this.setCache(key,o);
                 }
             }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public <T> T getCache(String key, Class<T> clazz, long timeout, TimeUnit timeUnit, CacheCallBack<T, String> callBack) {
        T o = null;
        try {
            o = this.getCache(key,clazz);
            if(o==null){
                log.info("未命中缓存，执行CacheCallBack回调函数");
                o = callBack.execute(key);
                if(o!=null){
                    this.setCacheExpire(key,o,timeout,timeUnit);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public boolean deleteCache(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
