package com.laowan.redis.service;

import java.util.concurrent.TimeUnit;

/**
 * @program: redis
 * @description: 缓存工具类
 * @author: wanli
 * @create: 2020-05-12 09:42
 **/
public interface CacheService {
    /**
     * 直接设置缓存
     * @param key
     * @param value
     * @return
     */
    boolean setCache(String key,Object value);

    /**
     * 设置缓存并设置过期时间
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     * @return
     */
    boolean setCacheExpire(String key, Object value, long timeout, TimeUnit timeUnit);


    /**
     * 不设置回调返回的获取方法
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
     <T> T  getCache(String key,Class<T> clazz);


    /**
     * 传递回调方法，重设缓存时设置过期时间
     * @param key 键
     * @return 值
     */
     <T> T  getCache(String key,Class<T> clazz,long timeout, TimeUnit timeUnit,CacheCallBack<T,String> callBack);


     <T> T  getCache(String key,Class<T> clazz,CacheCallBack<T,String> callBack);


    /**
     * 删除缓存
     * @param key
     * @return
     */
    boolean deleteCache(String key);
}
