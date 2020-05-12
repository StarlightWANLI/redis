package com.laowan.redis.service;

/**
 * @program: redis
 * @description: 缓存回调接口
 * @author: wanli
 * @create: 2020-05-12 09:43
 **/
public interface CacheCallBack <O,I> {
    O execute(I input);
}
